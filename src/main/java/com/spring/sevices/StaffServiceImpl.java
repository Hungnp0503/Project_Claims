package com.spring.sevices;

import com.spring.entities.Staff;
import com.spring.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;

    @Autowired
    public StaffServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public List<Staff> readAll() {
        List<Staff> staffList = staffRepository.findAll();
        return staffList;
    }
}
