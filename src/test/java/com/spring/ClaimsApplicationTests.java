package com.spring;

import com.spring.entities.Claims;
import com.spring.entities.Status;
import com.spring.reponsitory.ClaimsRepository;
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
	@Test
	void contextLoads() {
//		List<Claims> list = new ArrayList<>();
//		Status statusEnum = Status.valueOf("Pending_Approval");
//		list = service.findByStatus(statusEnum);
//		for(Claims claim : list) {
//			System.out.println(claim.toString());
//		}
	}

}
