package com.spring.entities;

import com.spring.validation.CreateGroup;
import com.spring.validation.UpdateGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

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
    @NotBlank(message = "{staff.blank.staffName}", groups = {UpdateGroup.class, CreateGroup.class})
    private String staffName;

    @Column(nullable = false)
    @NotBlank(message = "{staff.blank.email}", groups = {UpdateGroup.class, CreateGroup.class})
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "{staff.blank.password}", groups = {UpdateGroup.class, CreateGroup.class})
    private String password;

    @Column(nullable = false)
    @NotBlank(message = "{staff.blank.department}", groups = {UpdateGroup.class, CreateGroup.class})
    private String department;

    @Column(nullable = false)
    @NotBlank(message = "{staff.blank.jobRank}", groups = {UpdateGroup.class, CreateGroup.class})
    private String jobRank;

    @Column(nullable = false)
    @NotNull(message = "{staff.null.salary}", groups = {UpdateGroup.class, CreateGroup.class})
    @Positive
    private Double salary;

    @OneToMany(mappedBy = "staff")
    private List<ProjectDetail> projectDetails = new ArrayList<>();

    @OneToMany(mappedBy = "staff")
    private List<Claims> claims = new ArrayList<>();
}
