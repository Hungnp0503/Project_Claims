package com.spring.entities;

import com.spring.validation.CreateGroup;
import com.spring.validation.UpdateGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "{project.blank.name}", groups = {UpdateGroup.class, CreateGroup.class} )
    @Column(nullable = false)
    private String projectName;


    @NotBlank(message = "{project.blank.code}", groups = {UpdateGroup.class, CreateGroup.class} )
    @Size(max = 20,message = "{project.size.code}", groups = {UpdateGroup.class, CreateGroup.class})
    @Column(nullable = true, length = 20)
    private String projectCode;

    @NotNull(message = "{project.blank.fromDate}", groups = {UpdateGroup.class, CreateGroup.class} )
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fromDate;

    @NotNull(message = "{project.blank.toDate}", groups = {UpdateGroup.class, CreateGroup.class} )
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate toDate;

    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL)
    private List<ProjectDetail> projectDetails = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<Claims> claims = new ArrayList<>();

}
