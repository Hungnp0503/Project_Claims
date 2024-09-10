package com.spring.rest;

import com.spring.dto.StaffDTO;
import com.spring.repository.ProjectDetailCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/")
public class StaffRestController {

    private final ProjectDetailCustom projectDetailCustom;

    @Autowired
    public StaffRestController(ProjectDetailCustom projectDetailCustom) {
        this.projectDetailCustom = projectDetailCustom;
    }

    @RequestMapping("/staff")
    public List<StaffDTO> getStaff() {

        List<StaffDTO> staffDTOS = projectDetailCustom.getStaffNull();
        return staffDTOS;
    }

    @RequestMapping("/project/{id}")
    public List<StaffDTO> getObjects(@PathVariable("id") Integer id) {

        List<StaffDTO> staffDTOS = projectDetailCustom.getObjects(id);
        return staffDTOS;
    }
}
