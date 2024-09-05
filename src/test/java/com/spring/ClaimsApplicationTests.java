package com.spring;

import com.spring.entities.*;
import com.spring.reponsitory.ClaimsRepository;
import com.spring.reponsitory.ProjectDetailReponsitory;
import com.spring.reponsitory.ProjectReponsitory;
import com.spring.reponsitory.StaffReponsitory;
import com.spring.service.ClaimsService;
import com.spring.service.ClaimsServiceImple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ClaimsApplicationTests {
	@Autowired
	ClaimsRepository service ;
	@Autowired
	StaffReponsitory staffReponsitory ;
	@Autowired
	ProjectDetailReponsitory projectDetailReponsitory ;
	@Autowired
	ProjectReponsitory projectReponsitory ;
	@Test
	void contextLoads() {
//		List<Claims> list = new ArrayList<>();
//		Status statusEnum = Status.valueOf("Pending_Approval");
//		list = service.findByStatus(statusEnum);
//		for(Claims claim : list) {
//			System.out.println(claim.toString());
//		}
		String email ="staff1@gmail.com";
		Staff staff = staffReponsitory.findByEmail(email);
		if(staff!=null){
//			model.addAttribute("staff",staff);
//			model.addAttribute("claims", new Claims());
//			session.setAttribute("staffSesion",staff);
			List<ProjectDetail> projectDetails = projectDetailReponsitory.findByProjectDetailKeyStaffId(staff.getId());
			List<Integer> ids = new ArrayList<>();

			for (ProjectDetail projectDetail : projectDetails) {
				ids.add(projectDetail.getProject().getId());
			}

			for(Integer id : ids){
				System.out.println("Project ID : "+id);
				ProjectDetailKey projectDetailKey = new ProjectDetailKey();
				projectDetailKey.setProjectId(id);
				projectDetailKey.setStaffId(staff.getId());
               ProjectDetail projectDetails1 = projectDetailReponsitory.findByProjectDetailKey(projectDetailKey);
			    System.out.println(projectDetails1);
			}

//			List<Project> projects = projectReponsitory.findAllById(ids);
//			for(Project project : projects) {
//			    System.out.println(project.getProjectDetails());
//			}
		}
	}

}
