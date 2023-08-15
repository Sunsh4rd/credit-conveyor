package com.sunshard.deal.entity.json;

import com.sunshard.deal.model.enums.EmploymentPosition;
import com.sunshard.deal.model.enums.EmploymentStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Employment {
    private EmploymentStatus status;
    private String employerINN;
    private BigDecimal salary;
    private EmploymentPosition position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;
}
