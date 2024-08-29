package com.spring.sevices;

import com.spring.dto.ProjectDTO;
import com.spring.entities.Project;

import java.util.List;

public interface ProjectService {
    ProjectDTO convertProjectToProjectDTO(Project project, ProjectDTO projectDTO);
    Project convertProjectDTOToProject(ProjectDTO projectDTO,Project project);
    List<ProjectDTO> readAll();
    ProjectDTO readOne(Integer id);
    ProjectDTO save(ProjectDTO projectDTO);
    ProjectDTO delete(Integer id);
}
