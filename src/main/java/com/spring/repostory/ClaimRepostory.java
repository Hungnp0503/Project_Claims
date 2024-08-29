package com.spring.repostory;

import com.spring.entities.Claims;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClaimRepostory extends JpaRepository<Claims, Long> {
    List<Claims> findByStatus(String status);
}