package com.spring.repository;

import com.spring.entities.Claims;
import com.spring.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClaimRepository extends JpaRepository<Claims, Integer> {
    @Query(value = "SELECT * FROM claims WHERE id IN :ids", nativeQuery = true)
    List<Claims> findAllById(List<Integer> ids);

    List<Claims> findByStatusNot(Status status);


}