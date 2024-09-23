package com.spring.controller;

import com.spring.entities.*;
import com.spring.repository.ProjectDetailRepository;
import com.spring.sevices.ClaimService;
import com.spring.sevices.AuthServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/claims-requests")
public class ClaimController {
    @Autowired
    private AuthServices authServices;
    @Autowired
    private ClaimService claimService;
    @Autowired
    private ProjectDetailRepository projectDetailRepository;

    @GetMapping("/view/{id}")
    public String viewClaimDetails(@PathVariable("id") int id, Model model) {
        Claims claims = claimService.getClaimById(id);
        if (claims == null) {
            return "redirect:/claims-requests";
        }
        List<ClaimsDetails> claimDetails = claims.getClaimDays(); // Có thể truy cập danh sách claimDetails vì session vẫn mở
        model.addAttribute("claims", claims);
        model.addAttribute("claimDetails", claimDetails);
        return "layout/claim-request/claim-details";
    }

    @GetMapping("/access")
    public String  checkProjectAccess( Model model) {
        // Lấy thông tin nhân viên từ SecurityContext
        Staff staffDetails =  authServices.getCurrentUser().getStaffDb();
        Integer staffId = staffDetails.getStaffId();

        // Lấy roleProject từ ProjectDetail
        String roleProject = projectDetailRepository.findRoleProjectByStaffAndProject(staffId);

        // Kiểm tra roleProject và thực hiện xử lý tiếp theo
        // Kiểm tra roleProject và chuyển hướng
        if ("PM".equals(roleProject) || staffDetails.getRoleStaff().equals(RoleStaff.ADMIN)) {
            model.addAttribute("message", "Access granted as Project Manager");
            return "redirect:/claims-requests"; // Trả về trang của Project Manager
        } else {
            model.addAttribute("error", "Access denied");
            return "redirect:/claims/view"; // Trả về trang từ chối truy cập
        }
    }

    @GetMapping
    public String showClaimsRequests(Model model) {
//        Staff staffDetails =  authServices.getCurrentUser().getStaffDb();

        //TODO List<ProjectDetail> projectDetailList = projectDetailRepository
        List<Claims> claims = claimService.getAllClaims();
        model.addAttribute("claims", claims);
        
        return "layout/claim-request/claims-request";
    }

    @GetMapping("/approve/{id}")
    public String approveClaim(@PathVariable("id") int id, Model model) {
        Claims claim = claimService.getClaimById(id);
        if (claim != null && "Pending_Approval".equals(claim.getStatus().toString())) {
            claimService.updateClaimStatus(id, "Approved");
        }
        return "redirect:/claims-requests";
    }

    @GetMapping("/reject/{id}")
    public String rejectClaim(@PathVariable("id") int id, Model model) {
        Claims claim = claimService.getClaimById(id);
        if (claim != null && "Pending_Approval".equals(claim.getStatus().toString())) {
            claimService.updateClaimStatus(id, "Rejected");
        }
        model.addAttribute("message", "Claim rejected successfully.");
        return "redirect:/claims-requests";
    }

    @GetMapping("/pay/{id}")
    public String payClaim(@PathVariable("id") int id, Model model) {
        Claims claim = claimService.getClaimById(id);
        if (claim != null && "Approved".equals(claim.getStatus().toString())) {
            claimService.updateClaimStatus(id, "Paid");
        }
        model.addAttribute("message", "Claim marked as paid successfully.");
        return "redirect:/claims-requests";
    }

    @GetMapping("/pay-multiple/{ids}")
    public String payMultipleClaims(@PathVariable("ids") String ids, Model model) {
        List<Integer> claimIds = Arrays.stream(ids.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        claimService.updateMultipleClaimsStatus(claimIds, "Paid");
        model.addAttribute("message", "Selected claims marked as paid successfully.");
        return "redirect:/claims-requests";
    }

    @GetMapping("/cancel/{id}")
    public String cancelClaim(@PathVariable("id") int claimId) {
        claimService.cancelClaim(claimId);
        return "redirect:/claims-requests";
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadClaims(@RequestParam("ids") String ids) throws IOException {
        List<Integer> claimIds = Arrays.stream(ids.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        ByteArrayInputStream bais = claimService.generateClaimReport(claimIds);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=claims.csv");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(bais));
    }
}
