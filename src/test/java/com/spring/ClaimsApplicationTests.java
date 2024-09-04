package com.spring;

import com.spring.dto.StaffDTO;
import com.spring.entities.ProjectDetail;
import com.spring.repository.ProjectDetailCustom;
import com.spring.sevices.EmailService;
import com.spring.sevices.ProjectDetailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.context.Context;

import java.util.List;

@SpringBootTest
class ClaimsApplicationTests {

	private final ProjectDetailService projectDetailService;
	private final ProjectDetailCustom custom;
	private final EmailService emailService;

	@Autowired
    ClaimsApplicationTests(ProjectDetailService projectDetailService, ProjectDetailCustom custom, EmailService emailService) {
        this.projectDetailService = projectDetailService;
        this.custom = custom;
        this.emailService = emailService;
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
		List<StaffDTO> resulft = custom.getObjects(27);
		for(StaffDTO res : resulft){
			System.out.println(res);
		}
	}

}
