package com.spring.controller;

import com.spring.entities.ProjectDetail;
import com.spring.entities.Staff;
import com.spring.repository.ClaimsRepository;
import com.spring.repository.ProjectDetailRepository;
import com.spring.repository.StaffRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    ProjectDetailRepository projectDetail;
    @ModelAttribute("projectLogin")
    public List<Integer> getProjectLogin(HttpSession session) {
        return (List<Integer>) session.getAttribute("projectLogin");
    }
    @GetMapping("/login")
    public String loginForm(Model model, HttpSession session) {
        Object userLogger = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userLogger instanceof UserDetails) {
            Staff staff = staffRepository.findByEmail(((UserDetails) userLogger).getUsername());
            List<ProjectDetail> projectDetails = projectDetail.findByProjectDetailKeyStaffId(staff.getStaffId());
            List<Integer> id= new ArrayList<>();
            for (ProjectDetail projectDetail : projectDetails) {
                id.add(projectDetail.getProjectDetailKey().getProjectId());
            }
            // Kiểm tra nếu có dự án và lấy dự án đầu tiên (hoặc xử lý theo cách bạn cần)
            if (!id.isEmpty()) {
                session.setAttribute("projectLogin", id); // hoặc xử lý khác
            }
            return "redirect:/claims/view";
        } else {
            model.addAttribute("staff", new Staff());
            return "login";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }


}
