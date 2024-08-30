package com.spring.repository;

import com.spring.dto.ProjectDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProjectDetailCustom extends Repository {

    List<ProjectDTO> getObjects(Integer id);
    void delete(Integer id);
}
