package com.spring.sevices;

import com.spring.dto.ProjectDTO;
import com.spring.entities.Project;
import com.spring.entities.ProjectDetail;
import com.spring.entities.ProjectDetailKey;
import com.spring.repository.ProjectDetailRepository;
import com.spring.repository.ProjectRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectDetailServiceImpl implements ProjectDetailService{

    private final ProjectDetailRepository  projectDetailRepository;


    public ProjectDetailServiceImpl(ProjectDetailRepository projectDetailRepository) {
        this.projectDetailRepository = projectDetailRepository;
    }

    @Override
    public List<ProjectDetail> readAll() {
        return List.of();
    }

    @Override
    public ProjectDetail readOne(ProjectDetailKey id) {
        return null;
    }

    @Override
    public List<ProjectDetail> readOneProjectId(Integer id) {

        List<ProjectDetail> projectDetail = projectDetailRepository.findByProjectId(id);

        return projectDetail;
    }

    @Override
    public ProjectDetail save(ProjectDetail projectDetail) {

        projectDetailRepository.save(projectDetail);
        return projectDetail;
    }

    @Override
    public ProjectDetail delete(ProjectDetailKey id) {
        return null;
    }


}
