package com.spring.sevices;

import com.spring.dto.ProjectDTO;
import com.spring.entities.Project;

import java.util.List;

public interface ProjectService {
//    ProjectDTO convertProjectToProjectDTO(Project project, ProjectDTO projectDTO);
//    Project convertProjectDTOToProject(ProjectDTO projectDTO,Project project);
    List<Project> readAll();
    Project readOne(Integer id);
    Project save(Project project);
    Project delete(Integer id);
}
