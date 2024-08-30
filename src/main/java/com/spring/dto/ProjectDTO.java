package com.spring.dto;



import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {

    private Integer projectId;
    private Integer staffId;
    private String position;
    private String staffName;



}
