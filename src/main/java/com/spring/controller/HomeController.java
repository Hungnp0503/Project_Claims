package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/test/template")
    public String test() {
        return "layout/template";
    }

    @GetMapping("/")
    public String homePage() {
        return "home";
    }
}
