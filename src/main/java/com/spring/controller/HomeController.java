package com.spring.controller;

import com.spring.dto.ProjectDTO;
import com.spring.entities.Project;
import com.spring.entities.Staff;
import com.spring.sevices.ProjectService;
import com.spring.sevices.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HomeController {

    private final StaffService staffService;
    private final ProjectService projectService;

    @ModelAttribute("staffList")
    public List<Staff> populateStaffList() {
        return staffService.readAll();
    }
    @Autowired
    public HomeController(StaffService staffService, ProjectService projectService) {
        this.staffService = staffService;
        this.projectService = projectService;
    }

    @GetMapping("/test/template")
    public String test() {
        return "layout/template";
    }

    @GetMapping("/project/create")
    public String homePage(Model model) {
        model.addAttribute("dto", new ProjectDTO());
        return "project/create-project";
    }

    @GetMapping("/list")
    public String listProject(Model model) {
        List<ProjectDTO> projectDTOS = projectService.readAll();
        model.addAttribute("dto", projectDTOS);
        return "project/list-project";
    }

    @PostMapping("/project/save")
    public String saveProject(@ModelAttribute("dto") ProjectDTO projectDTO, Model model) {
        projectService.save(projectDTO);
        return "redirect:/list";
    }

    @GetMapping("/project/delete")
    public String delete(@RequestParam("id") Integer id, RedirectAttributes attributes) {

        projectService.delete(id);
        attributes.addFlashAttribute("message", "Project deleted successfully");
        return "redirect:/list";
    }

    @GetMapping("/project/edit")
    public String edit(@RequestParam("id") Integer id,Model model) {

        ProjectDTO projectDTO = projectService.readOne(id);
        model.addAttribute("dto",projectDTO);
        return "project/create-project";
    }

}
