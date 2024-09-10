package com.spring.reponsitory;


import com.spring.entities.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffReponsitory extends JpaRepository<Staff,Integer> {
    Staff findByEmail(String email);
    Staff findByStaffName(String staffName);
}
