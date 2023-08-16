package com.sunshard.deal.entity.json;

import com.sunshard.deal.model.enums.EmploymentPosition;
import com.sunshard.deal.model.enums.EmploymentStatus;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class Employment implements Serializable {
    private EmploymentStatus status;
    private String employerINN;
    private BigDecimal salary;
    private EmploymentPosition position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;
}
