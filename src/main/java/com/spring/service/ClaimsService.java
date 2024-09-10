package com.spring.service;

import com.spring.entities.Claims;

import java.util.List;

public interface ClaimsService {
    Claims saveClaim(Claims claim);
    List<Claims> getAllClaims();
}