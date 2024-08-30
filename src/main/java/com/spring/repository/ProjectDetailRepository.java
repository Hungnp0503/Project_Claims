package com.spring.repository;

import com.spring.entities.ProjectDetail;
import com.spring.entities.ProjectDetailKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectDetailRepository extends JpaRepository<ProjectDetail, ProjectDetailKey> {

    List<ProjectDetail> findByProjectId(Integer projectId);
    @Query("DELETE FROM ProjectDetail p WHERE p.projectDetailKey.projectId = :id")
    List<ProjectDetail> deleteId(@Param("id") Integer projectId);

}
