package com.spring.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = {"projectDetails","claims"})
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String projectName;

    @Column(nullable = true, length = 20)
    private String projectCode;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fromDate;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate toDate;

    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL)
    private List<ProjectDetail> projectDetails = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<Claims> claims = new ArrayList<>();

}
