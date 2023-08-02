package com.sunshard.conveyor.model;

import com.sunshard.conveyor.model.enums.EmploymentStatus;
import com.sunshard.conveyor.model.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class EmploymentDTO {
    private EmploymentStatus employmentStatus;
    private String employerINN;
    private BigDecimal salary;
    private Position position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;
}
