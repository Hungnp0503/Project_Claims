package com.spring.reponsitory;

import com.spring.entities.Claims;
import com.spring.entities.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClaimsRepository extends JpaRepository<Claims,Integer> {
    Page<Claims> findByStatus (Status status, Pageable pageable);
    List<Claims> findAllByOrderByStaff_StaffNameAsc();
    Optional<Claims> findById(Integer id);
}
