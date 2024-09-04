package com.spring.repository;


import com.spring.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Integer> {

    Project findByProjectName(String name);
    List<Project> findAllById(Iterable<Integer> ids);

}