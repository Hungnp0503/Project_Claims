package com.spring.dto;



import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class ProjectDTO {

    private Integer id;

    private String projectName;

    private String projectCode;

    private LocalDate fromDate;

    private LocalDate toDate;

    private String[] staffIdDTO;

    private String[] positionDTO;

    private String[] staffNameDTO;

}
