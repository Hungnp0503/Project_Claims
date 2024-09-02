package com.spring.repository;

import com.spring.dto.ProjectDTO;
import com.spring.entities.Staff;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProjectDetailCustom extends Repository {

    List<ProjectDTO> getObjects(Integer id);
    List<ProjectDTO> getStaffNull();
    void delete(Integer id);
}
