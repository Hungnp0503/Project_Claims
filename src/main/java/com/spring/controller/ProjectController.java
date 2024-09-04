package com.spring.controller;

import com.spring.dto.StaffDTO;
import com.spring.entities.Project;
import com.spring.entities.ProjectDetail;
import com.spring.entities.ProjectDetailKey;
import com.spring.entities.Staff;
import com.spring.repository.ProjectDetailCustom;
import com.spring.sevices.ProjectDetailService;
import com.spring.sevices.ProjectService;
import com.spring.validation.CreateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectDetailService projectDetailService;
    private final ProjectDetailCustom projectDetailCustom;

    @Autowired
    public ProjectController( ProjectService projectService, ProjectDetailService projectDetailService, ProjectDetailCustom projectDetailCustom) {
        this.projectService = projectService;
        this.projectDetailService = projectDetailService;
        this.projectDetailCustom = projectDetailCustom;
    }

    @GetMapping("/test/template")
    public String test() {
        return "layout/template";
    }

    @GetMapping("/project/create")
    public String homePage(Model model) {
        List<StaffDTO> staffDTOS = projectDetailCustom.getStaffNull();
        model.addAttribute("staffList", staffDTOS);
        model.addAttribute("project", new Project());
        return "project/create-project";
    }

    @GetMapping("/list")
    public String listProject(Model model) {
        List<Project> projects = projectService.readAll();
        model.addAttribute("projectList", projects);
        return "project/list-project";
    }

    @PostMapping("/project/save")
    public String saveProject(
            @Validated(CreateGroup.class)
            @ModelAttribute("project")
            Project project,
            BindingResult bindingResult,
            @RequestParam("staffId") String staffId,
            @RequestParam("position") String role,
            RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return "/project/create-project";
        }
        if(project.getFromDate().getDayOfMonth() >= project.getToDate().getDayOfMonth()){
            attributes.addFlashAttribute("message","To date must be greater than  from date");
            return "redirect:/project/create";
        }
        projectService.save(project);
        Integer id = project.getId();
        projectDetailCustom.delete(id);
        String[] staffIdArray = staffId.split(",");
        String[] positionArray = role.split(",");
        System.out.println(staffIdArray.length + " members added and");
        System.out.println(positionArray.length + " members added and");
        int count=0;
        for(String i : positionArray){
            if (i.equals("PM")) {
                count++;
            }
        }
        if(count == 0 || count >=2){
            attributes.addFlashAttribute("message","A project has only 1 PM");
            return "redirect:/project/create";
        }
        count=0;
        for(String i : positionArray){
            if (i.equals("QA")) {
                count++;
            }
        }
        if(count == 0 || count >=2){
            attributes.addFlashAttribute("message","A project has only 1 QA");
            return "redirect:/project/create";
        }

        List<ProjectDetail> projectDetailList = new ArrayList<>();

        for (int i = 0; i < staffIdArray.length; i++) {
            ProjectDetailKey projectDetailKey = new ProjectDetailKey(id, Integer.valueOf(staffIdArray[i]));
            String position = positionArray[i];
            if(!position.equals("0")){
                ProjectDetail projectDetail = new ProjectDetail();
                projectDetail.setProjectDetailKey(projectDetailKey);
                projectDetail.setRoleProject(position);

                Project project1 = new Project();
                project1.setId(id);
                projectDetail.setProject(project);

                Staff staff = new Staff();
                staff.setStaffId(Integer.parseInt(staffIdArray[i]));
                projectDetail.setStaff(staff);

                projectDetailList.add(projectDetail);
            }
        }
        for(ProjectDetail detail : projectDetailList){
            projectDetailService.save(detail);
        }

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
        Project project = projectService.readOne(id);
        List<StaffDTO> staffDTOS = projectDetailCustom.getObjects(id);
        model.addAttribute("project",project);
        model.addAttribute("staffList", staffDTOS);
        return "project/create-project";
    }

}
