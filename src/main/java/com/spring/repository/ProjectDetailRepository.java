package com.spring.repository;

import com.spring.entities.ProjectDetail;
import com.spring.entities.ProjectDetailKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ProjectDetailRepository extends JpaRepository<ProjectDetail, ProjectDetailKey> {

    List<ProjectDetail> findByProjectId(Integer projectId);


    ProjectDetail findByProjectIdAndRoleProject(Integer projectId,String role);
    List<ProjectDetail> findByProjectDetailKeyStaffId(Integer staffId);
    ProjectDetail findByProjectDetailKey(ProjectDetailKey projectDetailKey);

    @Query("SELECT pd.roleProject FROM ProjectDetail pd WHERE pd.projectDetailKey.staffId = :staffId ")
    String findRoleProjectByStaffAndProject(@Param("staffId") Integer staffId);


}
