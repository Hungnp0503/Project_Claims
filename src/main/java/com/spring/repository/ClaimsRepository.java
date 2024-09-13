package com.spring.repository;

import com.spring.entities.Claims;
import com.spring.entities.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClaimsRepository extends JpaRepository<Claims,Integer> {
    @Query("SELECT c FROM Claims c WHERE c.status = 'Pending_Approval'")
    List<Claims> findPendingApprovalClaims();

    Page<Claims> findByStatus (Status status, Pageable pageable);
    List<Claims> findAllByOrderByStaff_StaffNameAsc();
    Optional<Claims> findById(Integer id);

}
