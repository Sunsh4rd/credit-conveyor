package com.sunshard.conveyor.model;

import com.sunshard.conveyor.model.enums.EmploymentStatus;
import com.sunshard.conveyor.model.enums.Position;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "Loaner's employment data")
public class EmploymentDto {

    @Schema(
            type = "enum",
            example = "SELF_EMPLOYED",
            description = "loaner's employment status"
    )
    @NotNull
    private EmploymentStatus employmentStatus;

    @Schema(
            type = "string",
            example = "012345678987",
            description = "loaner's employer INN"
    )
    @Digits(integer = 12, fraction = 0)
    @NotEmpty
//    @Pattern(regexp = "^[0-9]{12}$")
    private String employerINN;

    @Schema(
            type = "integer",
            example = "50000",
            description = "loaner's salary"
    )
    @NotNull
    private BigDecimal salary;

    @Schema(
            type = "enum",
            example = "TOP_MANAGER",
            description = "loaner's position"
    )
    @NotNull
    private Position position;

    @Schema(
            type = "integer",
            example = "12",
            description = "loaner's total work experience in months"
    )
    @PositiveOrZero
    @NotNull
    private Integer workExperienceTotal;

    @Schema(
            type = "integer",
            example = "5",
            description = "loaner's current work experience in months"
    )
    @PositiveOrZero
    @NotNull
    private Integer workExperienceCurrent;
}
