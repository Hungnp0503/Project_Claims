package com.spring.controller;


import com.spring.entities.*;
import com.spring.reponsitory.ClaimsRepository;
import com.spring.reponsitory.ProjectDetailReponsitory;
import com.spring.reponsitory.ProjectReponsitory;
import com.spring.reponsitory.StaffReponsitory;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CreateClaimsController {
    @Autowired
    private ProjectReponsitory projectReponsitory;

    @Autowired
    private StaffReponsitory staffReponsitory;

    @Autowired
    private ProjectDetailReponsitory projectDetailReponsitory;
    @Autowired
    private ClaimsRepository claimsRepository;


    @GetMapping("/createClaims")
    public String claimsPage( Model model, HttpSession session
                             ) {
        // Lấy dữ liệu từ database và truyền vào view
        String email ="staff1@gmail.com";
        Staff staff = staffReponsitory.findByEmail(email);
        if(staff!=null){
            model.addAttribute("staff",staff);
            model.addAttribute("claims", new Claims());
            session.setAttribute("staffSesion",staff);
            List<ProjectDetail> projectDetails = projectDetailReponsitory.findByProjectDetailKeyStaffId(staff.getId());
            List<Integer> ids = new ArrayList<>();

            for (ProjectDetail projectDetail : projectDetails) {
                ids.add(projectDetail.getProject().getId());
            }

            List<Project> projects = projectReponsitory.findAllById(ids);
            model.addAttribute("projects",projects);

        }

        return "claims/createClaims";
    }
    @PostMapping("/claims/save")
    public String saveClaims(@RequestParam("projectName") String id,
                             @RequestParam("action") String action,
                             @ModelAttribute("claims") Claims claims,
                             Model model,
                             HttpSession session){
        Integer projectid = Integer.parseInt(id);
        // Lưu dữ liệu vào database
        Staff staff = (Staff) session.getAttribute("staffSesion");
        claims.setStaff(staff);
        claims.setProject(projectReponsitory.findById(projectid).get());

        if ("save".equals(action)) {
            claims.setStatus(Status.Draft);
        } else if ("submit".equals(action)) {
            claims.setStatus(Status.Pending_Approval);
        }
        claimsRepository.save(claims);
        return "redirect:/claims/view";

    }

}
