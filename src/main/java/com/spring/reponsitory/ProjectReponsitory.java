package com.spring.reponsitory;


import com.spring.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectReponsitory extends JpaRepository<Project,Integer> {

    Project findByProjectName(String name);
    List<Project> findAllById(Iterable<Integer> ids);

    Optional<Project> findById (Integer id);

}
