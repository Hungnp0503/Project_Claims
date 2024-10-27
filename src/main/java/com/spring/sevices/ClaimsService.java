package com.spring.sevices;

import com.spring.entities.Claims;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface ClaimsService {
    Claims saveClaim(Claims claim);
    List<Claims> getAllClaims();

    void export(List<Claims> lists, HttpServletResponse response) throws IOException, IllegalAccessException;
}
