package com.spring.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProjectDetail {

    @EmbeddedId
    private ProjectDetailKey projectDetailKey;

    private String roleProject;

    @ManyToOne
    @MapsId("projectId")
    private Project project;

    @ManyToOne
    @MapsId("staffId")
    private Staff staff;


}
