package com.spring.sevices;

import com.spring.entities.Claims;
import com.spring.repository.ClaimsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClaimsServiceImple implements ClaimsService {
    @Autowired
    private ClaimsRepository claimsRepository;
    @Override
    public Claims saveClaim(Claims claim) {

        claimsRepository.save(claim);

        return claim;
    }

    @Override
    public List<Claims> getAllClaims() {
        return claimsRepository.findAll();
    }
}