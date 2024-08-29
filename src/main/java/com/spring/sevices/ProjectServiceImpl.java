package com.spring.sevices;

import com.spring.dto.ProjectDTO;
import com.spring.entities.Project;
import com.spring.repository.ProjectRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectDTO convertProjectToProjectDTO(Project project, ProjectDTO projectDTO) {
        if(projectDTO == null) {
            projectDTO = new ProjectDTO();
        }
        BeanUtils.copyProperties( project,projectDTO);

        String staffIdStr = project.getStaffId();
        String[] staffIdDTO = staffIdStr.split(",");
        projectDTO.setStaffIdDTO(staffIdDTO);

        String staffNameStr = project.getStaffName();
        String[] staffNameDTO = staffNameStr.split(",");
        projectDTO.setStaffNameDTO(staffNameDTO);

        String positionStr = project.getPosition();
        String[] positionDTO = positionStr.split(",");
        projectDTO.setPositionDTO(positionDTO);

        return projectDTO;
    }

    @Override
    public Project convertProjectDTOToProject(ProjectDTO projectDTO, Project project) {
        if(project == null) {
            project = new Project();
        }
        BeanUtils.copyProperties(projectDTO, project,"staffIdDTO","staffNameDTO","positionDTO");

        String[] staffId = projectDTO.getStaffIdDTO();
        StringBuilder staffIdStr = new StringBuilder();
        if(staffId!= null) {
            for (String id : staffId) {
                staffIdStr.append(id).append(",");
            }
        }
        project.setStaffId(staffIdStr.toString());

        String[] staffName = projectDTO.getStaffNameDTO();
        StringBuilder staffNameStr = new StringBuilder();
        if(staffName!= null) {
            for (String name : staffName) {
                staffNameStr.append(name).append(",");
            }
        }
        project.setStaffName(staffNameStr.toString());

        String[] position = projectDTO.getPositionDTO();
        StringBuilder positionStr = new StringBuilder();
        if(position!= null) {
            for (String pos : position) {
                positionStr.append(pos).append(",");
            }
        }
        project.setPosition(positionStr.toString());


        return project;
    }

    @Override
    public List<ProjectDTO> readAll() {
        List<ProjectDTO> projectDTOs = new ArrayList<>();
        List<Project> result = projectRepository.findAll() ;

        for (Project project : result) {
            ProjectDTO dto = convertProjectToProjectDTO(project,null);
            projectDTOs.add(dto);
        }
        return projectDTOs;
    }

    @Override
    public ProjectDTO readOne(Integer id) {
        Project project = projectRepository.findById(id).orElse(null);
        ProjectDTO projectDTO = convertProjectToProjectDTO(project,null);
        return projectDTO;
    }

    @Override
    public ProjectDTO save(ProjectDTO projectDTO) {
        Project project = convertProjectDTOToProject(projectDTO,null);
        projectRepository.save(project);
        projectDTO.setId(project.getId());
        return projectDTO;
    }

    @Override
    public ProjectDTO delete(Integer id) {
        Project project = projectRepository.findById(id).orElse(null);
        if(project!= null) {
            projectRepository.delete(project);
        }
        return convertProjectToProjectDTO(project,null);
    }
}
