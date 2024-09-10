package com.spring.reponsitory;

import com.spring.entities.Claims;
import com.spring.entities.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClaimsRepository extends JpaRepository<Claims,Integer> {
    Page<Claims> findByStatus (Status status, Pageable pageable);
    List<Claims> findAllByOrderByStaff_StaffNameAsc();
}
