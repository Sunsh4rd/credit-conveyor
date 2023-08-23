package com.sunshard.application.model;

import com.sunshard.application.model.enums.EmploymentStatus;
import com.sunshard.application.model.enums.Position;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Loaner's employment data")
public class EmploymentDTO {

    @Schema(
            type = "enum",
            example = "SELF_EMPLOYED",
            description = "loaner's employment status"
    )
    private EmploymentStatus employmentStatus;

    @Schema(
            type = "string",
            example = "012345678987",
            description = "loaner's employer INN"
    )
    @Pattern(regexp = "^[0-9]{12}$", message = "Employer INN should be 12 digits")
    private String employerINN;

    @Schema(
            type = "integer",
            example = "50000",
            description = "loaner's salary"
    )
    private BigDecimal salary;

    @Schema(
            type = "enum",
            example = "TOP_MANAGER",
            description = "loaner's position"
    )
    private Position position;

    @Schema(
            type = "integer",
            example = "12",
            description = "loaner's total work experience in months"
    )
    private Integer workExperienceTotal;

    @Schema(
            type = "integer",
            example = "5",
            description = "loaner's current work experience in months"
    )
    private Integer workExperienceCurrent;
}
