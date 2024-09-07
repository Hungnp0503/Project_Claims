package com.spring.controller;

import com.spring.entities.Staff;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String loginForm(Model model) {
        Object userLogger = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(userLogger instanceof UserDetails)) {
            model.addAttribute("staff", new Staff());
            return "login";
        }
        return "redirect:/home";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
