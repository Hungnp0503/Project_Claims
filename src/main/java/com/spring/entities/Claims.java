package com.spring.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Claims {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    private LocalDate date;

    private String day;

    @Temporal(TemporalType.TIME)
    private LocalTime fromDate;

    @Temporal(TemporalType.TIME)
    private LocalTime toDate;

    private Double totalOfHours;

    private Double totals;

//    @ManyToOne
//    @JoinColumn(name = "staff_id")
//    private Staff staff;
//
//    @ManyToOne
//    @JoinColumn(name = "project_id")
//    private Project project;


}
