package com.spring.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter

public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String staffName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String jobRank;

    @Column(nullable = false)
    private double salary;

    @OneToMany(mappedBy = "staff")
    private List<ProjectDetail> projectDetails = new ArrayList<>();

    @OneToMany(mappedBy = "staff")
    private List<Claims> claims = new ArrayList<>();

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", staffName='" + staffName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", department='" + department + '\'' +
                ", jobRank='" + jobRank + '\'' +
                ", salary=" + salary +
                '}';
    }
}
