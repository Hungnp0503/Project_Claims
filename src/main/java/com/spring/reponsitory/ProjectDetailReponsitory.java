package com.spring.reponsitory;

import com.spring.entities.ProjectDetail;
import com.spring.entities.ProjectDetailKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectDetailReponsitory extends JpaRepository<ProjectDetail, ProjectDetailKey> {
    List<ProjectDetail> findByProjectDetailKeyStaffId(Integer staffId);
    ProjectDetail findByProjectDetailKey(ProjectDetailKey projectDetailKey);
}
