package com.spring.repository;

import com.spring.entities.Claims;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClaimsRepository extends JpaRepository<Claims,Integer> {
    @Query("SELECT c FROM Claims c WHERE c.status = 'Pending_Approval'")
    List<Claims> findPendingApprovalClaims();

}