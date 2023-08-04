package com.sunshard.conveyor.model;

import com.sunshard.conveyor.model.enums.EmploymentStatus;
import com.sunshard.conveyor.model.enums.Position;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Loaner's employment data")
public class EmploymentDTO {

    @Schema(type = "enum",
            example = "SELF_EMPLOYED",
            description = "loaner's employment status"
    )
    private EmploymentStatus employmentStatus;

    @Schema(type = "string",
            example = "012345678987",
            pattern = "^[0-9]{12}$",
            description = "loaner's employer INN"
    )
    @Pattern(regexp = "^[0-9]{12}$")
    private String employerINN;

    @Schema(type = "integer",
            example = "50000",
            description = "loaner's salary"
    )
    private BigDecimal salary;

    @Schema(type = "enum",
            example = "TOP_MANAGER",
            description = "loaner's position"
    )
    private Position position;

    @Schema(type = "integer",
            example = "12",
            description = "loaner's total work experience in months"
    )
    private Integer workExperienceTotal;

    @Schema(type = "integer",
            example = "5",
            description = "loaner's current work experience in months"
    )
    private Integer workExperienceCurrent;
}
