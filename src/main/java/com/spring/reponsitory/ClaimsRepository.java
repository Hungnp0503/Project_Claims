package com.spring.reponsitory;

import com.spring.entities.Claims;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimsRepository extends JpaRepository<Claims,Integer> {

}
