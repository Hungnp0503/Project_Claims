package com.spring;

import com.spring.dto.ProjectDTO;
import com.spring.entities.ProjectDetail;
import com.spring.repository.ProjectDetailCustom;
import com.spring.sevices.ProjectDetailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class ClaimsApplicationTests {

	private final ProjectDetailService projectDetailService;
	private final ProjectDetailCustom custom;
	@Autowired
    ClaimsApplicationTests(ProjectDetailService projectDetailService, ProjectDetailCustom custom) {
        this.projectDetailService = projectDetailService;
        this.custom = custom;
    }

    @Test
	void contextLoads() {
		List<ProjectDetail> projectDetail = projectDetailService.readOneProjectId(44);
		for (ProjectDetail projectDetail1 : projectDetail){
			if(projectDetail1.getRoleProject().equals("PM")){
				System.out.println(projectDetail1.getStaff().getEmail());
			};
		}
	}

	@Test
	void contextLoads1() {
		List<ProjectDTO> resulft = custom.getObjects(27);
		for(ProjectDTO res : resulft){
			System.out.println(res);
		}
	}

}
