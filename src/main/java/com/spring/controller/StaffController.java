package com.spring.controller;

import com.spring.entities.Staff;
import com.spring.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;



@Controller
public class StaffController {

    @Autowired
    StaffRepository staffRepository;
    @RequestMapping(value="/staff/list",method = {RequestMethod.GET, RequestMethod.POST})
    public String staffListPage(
            Model model,
            @RequestParam(value = "page" ,defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "search" ,required = false) String search
    ) {
        int pageSizeDefault = 3;
        Pageable pageable = PageRequest.of(pageNumber-1, pageSizeDefault);
        Page<Staff> page = null;

        if(search == null || search.isBlank()){
            page = staffRepository.findAll(pageable);

        }else {
            page = staffRepository.findByStaffNameOrDepartmentOrJobRankOrEmail("%"+search+"%" ,pageable);
        }

        //tinh list page
        int totalPages = page.getTotalPages();


        List<Integer> pageNumber1 = new ArrayList<>();
        for (int i = 1; i <= totalPages; i++) {
            pageNumber1.add(i);
        }


        model.addAttribute ("pageNumber", pageNumber1);
        model.addAttribute("page", page);
        return "staff/staffList";
    }
}
