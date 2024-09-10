package com.spring.controller;

import com.spring.dto.StaffDTO;
import com.spring.entities.Project;
import com.spring.entities.ProjectDetail;
import com.spring.entities.ProjectDetailKey;
import com.spring.entities.Staff;
import com.spring.repository.ProjectDetailCustom;
import com.spring.repository.ProjectRepository;
import com.spring.sevices.ProjectDetailService;
import com.spring.sevices.ProjectService;
import com.spring.validation.CreateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectRepository projectRepository;
    private final ProjectDetailService projectDetailService;
    private final ProjectDetailCustom projectDetailCustom;

    @Autowired
    public ProjectController(ProjectService projectService, ProjectRepository projectRepository, ProjectDetailService projectDetailService, ProjectDetailCustom projectDetailCustom) {
        this.projectService = projectService;
        this.projectRepository = projectRepository;
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

    @RequestMapping(value="/project/list",method = {RequestMethod.GET,RequestMethod.POST})
    public String listProject( @RequestParam(value = "page",defaultValue = "1") Integer pageNumber,
                               @RequestParam(value = "keyword",required = false) String keyword,
                               Model model) {

        int pageSize = 5;

        Pageable pageable = PageRequest.of(pageNumber-1,pageSize);
        Page<Project> pages =null;
        if(keyword == null || keyword.isBlank()) {
            pages= projectRepository.findAll(pageable);
        }else{
            pages =projectRepository.findBySearch("%"+keyword+"%",pageable);
        }

        int totalPage = pages.getTotalPages();
        List<Integer> pageNums = new ArrayList<>();
        for(int i=1 ;i<=totalPage;i++){
            pageNums.add(i);
        }

        model.addAttribute("page",pages);
        model.addAttribute("pageNums",pageNums);
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
            Model model,
            RedirectAttributes attributes) {

        List<StaffDTO> staffDTOS = projectDetailCustom.getStaffNull();
        model.addAttribute("staffList", staffDTOS);
        if (bindingResult.hasErrors()) {
            return "redirect:/project/create";
        }
        if(project.getFromDate().getDayOfMonth() >= project.getToDate().getDayOfMonth()){
            attributes.addFlashAttribute("message","To date must be greater than  from date");
            return "redirect:/project/create";
        }

        String[] staffIdArray = staffId.split(",");
        String[] positionArray = role.split(",");
//
        System.out.println(staffIdArray.length);
        System.out.println(positionArray.length);
        int count=0;
        for(String i : positionArray){
            if (i.equals("PM")) {
                count++;
            }
        }
        if(count == 0 || count >=2){
            attributes.addFlashAttribute("message","A project must has a PM");
            return "redirect:/project/create";
        }
        count=0;
        for(String i : positionArray){
            if (i.equals("QA")) {
                count++;
            }
        }
        if(count == 0 || count >=2 ){
            attributes.addFlashAttribute("message","A project must has a QA");
            return "redirect:/project/create";
        }

        projectService.save(project);
        Integer id = project.getId();
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

//        projectDetailCustom.delete(id);
        for(ProjectDetail detail : projectDetailList){
            projectDetailService.save(detail);
        }
//
        return "redirect:/project/list";
    }

    @GetMapping("/project/delete")
    public String delete(@RequestParam("id") Integer id, RedirectAttributes attributes) {

        projectService.delete(id);
        attributes.addFlashAttribute("message", "Project deleted successfully");
        return "redirect:/project/list";
    }

    @GetMapping("/project/edit")
    public String edit(@RequestParam("id") Integer id,Model model) {
        Project project = projectService.readOne(id);
        model.addAttribute("project",project);
        return "project/create-project";
    }
}