package com.spring.entities;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class ProjectDetailKey {
    
    private Integer projectId;
    
    private Integer staffId;
}
