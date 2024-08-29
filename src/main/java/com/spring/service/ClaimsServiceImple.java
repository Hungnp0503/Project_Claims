package com.spring.service;

import com.spring.entities.Claims;
import com.spring.reponsitory.ClaimsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ClaimsServiceImple implements ClaimsService{
    @Autowired
    private ClaimsRepository claimsRepository;
    @Override
    public Claims saveClaim(Claims claim) {

        claimsRepository.save(claim);

        return claim;
    }
}
