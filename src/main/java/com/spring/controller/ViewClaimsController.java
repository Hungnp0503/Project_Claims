package com.spring.controller;

import com.spring.entities.Claims;
import com.spring.entities.Status;
import com.spring.reponsitory.ClaimsRepository;
import com.spring.reponsitory.ProjectDetailReponsitory;
import com.spring.reponsitory.ProjectReponsitory;
import com.spring.reponsitory.StaffReponsitory;
import com.spring.service.ClaimsService;
import com.spring.service.ClaimsServiceImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ViewClaimsController {
    @Autowired
    private ProjectReponsitory projectReponsitory;

    @Autowired
    private StaffReponsitory staffReponsitory;

    @Autowired
    private ProjectDetailReponsitory projectDetailReponsitory;

    @Autowired
    private ClaimsRepository claimsRepository;

    @Autowired
    private ClaimsService claimsService = new ClaimsServiceImple();
    @GetMapping("/claims/view")
    public String viewClaims(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "5") int size,
                             @RequestParam(defaultValue = "All") String status,
                             Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Claims> claimsPage;

        if ("All".equals(status)) {
            claimsPage = claimsRepository.findAll(pageable);
        } else {
            claimsPage = claimsRepository.findByStatus(Status.valueOf(status), pageable);
        }

        model.addAttribute("claimsPage", claimsPage);
        model.addAttribute("status", status);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", claimsPage.getTotalPages());
        return "claims/ViewClaims";
    }
    
}
