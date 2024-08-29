package com.spring.service;

import com.spring.entities.Claims;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class ClaimService {
    @Autowired
    private ClaimRepository claimRepository;

    public List<Claims> getClaimsByStatus(String status) {
        return claimRepository.findByStatus(status);
    }

    public Claims saveClaim(Claims claim) {
        return claimRepository.save(claim);
    }

    public Claims updateClaimStatus(Long id, String status) {
        Claims claim = claimRepository.findById(id).orElseThrow(() -> new RuntimeException("Claim not found"));
        claim.setStatus(status);
        if ("Approved".equalsIgnoreCase(status)) {
            claim.setApprovedDate(LocalDateTime.now());
        }

        return claimRepository.save(claim);
    }



}
