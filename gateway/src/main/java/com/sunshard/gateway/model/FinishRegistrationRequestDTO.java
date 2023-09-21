package com.sunshard.gateway.model;

import com.sunshard.gateway.model.enums.Gender;
import com.sunshard.gateway.model.enums.MaritalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Loaner data for finishing registration gender")
public class FinishRegistrationRequestDTO {
    @Schema(
            type = "enum",
            example = "MALE",
            description = "loaner's gender"
    )
    private Gender gender;

    @Schema(
            type = "enum",
            example = "SINGLE",
            description = "loaner's marital status"
    )
    private MaritalStatus maritalStatus;

    @Schema(
            type = "integer",
            example = "3",
            description = "loaner's amount of dependents"
    )
    private Integer dependentAmount;

    @Schema(
            type = "string",
            format = "date",
            example = "2020-09-12",
            description = "loaner's passport issue date"
    )
    private LocalDate passportIssueDate;

    @Schema(
            type = "string",
            example = "Issue branch number 5",
            description = "loaner's passport issue branch"
    )
    private String passportIssueBranch;

    @Schema(description = "loaner's employment data")
    private EmploymentDTO employment;

    @Schema(
            type = "string",
            example = "98765432100123456789",
            description = "loaner's bank account"
    )
    private String account;
}
