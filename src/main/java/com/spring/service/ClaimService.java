package com.spring.service;

import com.spring.entities.Claims;
import com.spring.repository.ClaimRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

@Service
public class ClaimService {
    @Autowired
    private ClaimRepository claimRepository;

    public void updateClaimStatus(int id, String status) {
        Claims claim = claimRepository.findById(id).orElse(null);
        if (claim != null) {
            claim.setStatus(status);
            claimRepository.save(claim);
        }
    }

    public List<Claims> getAllClaims() {
        return claimRepository.findAll();
    }

    public Claims getClaimById(int id) {
        return claimRepository.findById(id).orElse(null);
    }

    public void updateMultipleClaimsStatus(List<Integer> ids, String status) {
        List<Claims> claims = claimRepository.findAllById(ids);
        for (Claims claim : claims) {
            claim.setStatus(status);
        }
        claimRepository.saveAll(claims);
    }

    public void cancelClaim(int claimId) {
        Claims claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));
        if ("Draft".equals(claim.getStatus())) {
            claim.setStatus("Cancelled");
            claimRepository.save(claim);
        } else {
            throw new RuntimeException("Cannot cancel a claim that is not in Draft status");
        }
    }

    public ByteArrayInputStream generateClaimReport(List<Integer> claimIds) throws IOException {
        List<Claims> claims = claimRepository.findAllById(claimIds);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (CSVPrinter printer = new CSVPrinter(new OutputStreamWriter(out), CSVFormat.DEFAULT.withHeader("ID", "Requester", "Status", "Date"))) {
            for (Claims claim : claims) {
                printer.printRecord(claim.getId(), claim.getStatus(), claim.getDate());
            }
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
}
