package com.sunshard.conveyor.model;

import com.sunshard.conveyor.model.enums.Gender;
import com.sunshard.conveyor.model.enums.MaritalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@Schema(description = "Loaner data for finishing registration gender")
public class FinishRegistrationRequestDto {

    @Schema(
            type = "enum",
            example = "MALE",
            description = "loaner's gender"
    )
    @NotNull
    private Gender gender;

    @Schema(
            type = "enum",
            example = "SINGLE",
            description = "loaner's marital status"
    )
    @NotNull
    private MaritalStatus maritalStatus;

    @Schema(
            type = "integer",
            example = "3",
            description = "loaner's amount of dependents"
    )
    @NotNull
    private Integer dependentAmount;

    @Schema(
            type = "string",
            format = "date",
            example = "2020-09-12",
            description = "loaner's passport issue date"
    )
    @NotNull
    private LocalDate passportIssueDate;

    @Schema(
            type = "string",
            example = "Issue branch number 5",
            description = "loaner's passport issue branch"
    )
    @NotBlank
    private String passportIssueBranch;

    @Schema(description = "loaner's employment data")
    private EmploymentDto employment;

    @Schema(
            type = "string",
            example = "98765432100123456789",
            description = "loaner's bank account"
    )
    @NotBlank
    private String account;
}
