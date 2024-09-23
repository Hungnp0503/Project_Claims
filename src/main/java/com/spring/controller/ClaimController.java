package com.spring.controller;

import com.spring.entities.Claims;
import com.spring.entities.RoleStaff;
import com.spring.entities.Staff;
import com.spring.repository.ClaimRepository;
import com.spring.repository.ProjectDetailRepository;
import com.spring.sevices.ClaimService;
import com.spring.sevices.AuthServices;
import com.spring.sevices.ClaimsService;
import com.spring.sevices.ProjectDetailService;
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
    AuthServices authServices;
    @Autowired
    private ClaimService claimService;

    @Autowired
    private ProjectDetailRepository projectDetailRepository;

    @Autowired
    private ClaimsService claimsService;
    @Autowired
    private ClaimRepository claimRepository;


    @GetMapping("/access")
    public String  checkProjectAccess( Model model) {
        // Lấy thông tin nhân viên từ SecurityContext
        Staff staffDetails =  authServices.getCurrentUser().getStaffDb();
        Integer staffId = staffDetails.getStaffId();

        // Lấy roleProject từ ProjectDetail
        String roleProject = projectDetailRepository.findRoleProjectByStaffAndProject(staffId);

        // Kiểm tra roleProject và thực hiện xử lý tiếp theo
        // Kiểm tra roleProject và chuyển hướng
        if ("PM".equals(roleProject)|| "QA".equals(roleProject)|| staffDetails.getRoleStaff().equals(RoleStaff.ADMIN)) {
            model.addAttribute("message", "Access granted as Project Manager");
            return "redirect:/claims-requests"; // Trả về trang của Project Manager
        } else {
            model.addAttribute("error", "Access denied");
            return "redirect:/claims/view"; // Trả về trang từ chối truy cập
        }
    }
    @GetMapping
    public String showClaimsRequests(Model model) {
        Staff staffDetails =  authServices.getCurrentUser().getStaffDb();
        Integer staffId = staffDetails.getStaffId();
        String roleProject = projectDetailRepository.findRoleProjectByStaffAndProject(staffId);


        if(staffDetails.getRoleStaff().toString().equals("ADMIN")){
            List<Claims> allClaims = claimService.getAllClaims();
            model.addAttribute("claims", allClaims);
            model.addAttribute("role","ADMIN");
        }else if(roleProject.equals("PM")) {
            List<Integer> projectId= projectDetailRepository.findProjectDetailKey_ProjectIdByProjectDetailKey_StaffIdAndRoleProject(staffId,"PM");
            for (Integer id : projectId){

                List<Claims> allClaims = claimRepository.findAllByProject_Id(id);
                model.addAttribute("claims", allClaims);
                model.addAttribute("role","PM");
            }
        }else{
            List<Integer> projectId= projectDetailRepository.findProjectDetailKey_ProjectIdByProjectDetailKey_StaffIdAndRoleProject(staffId,"QA");
            for (Integer id : projectId){

                List<Claims> allClaims = claimRepository.findAllByProject_Id(id);
                model.addAttribute("claims", allClaims);
                model.addAttribute("role","QA");
            }
        }
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
