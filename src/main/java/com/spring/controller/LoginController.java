package com.spring.controller;

import com.spring.entities.Staff;
import com.spring.repository.ClaimsRepository;
import com.spring.repository.StaffRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @Autowired
    StaffRepository staffRepository;
    @GetMapping("/login")
    public String loginForm(Model model,
        HttpSession session) {
        Object userLogger = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userLogger instanceof UserDetails) {
            Staff staff = staffRepository.findByEmail(((UserDetails) userLogger).getUsername());
            session.setAttribute("staffSession", staff);
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
