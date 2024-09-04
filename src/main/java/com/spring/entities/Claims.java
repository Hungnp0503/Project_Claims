package com.spring.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@ToString
public class Claims {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;


    private String day;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime fromDate;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime toDate;

    private Double totalOfHours;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String description;
    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;


}
