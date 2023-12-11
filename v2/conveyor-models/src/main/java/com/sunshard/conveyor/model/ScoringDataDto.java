package com.sunshard.conveyor.model;

import com.sunshard.conveyor.model.enums.Gender;
import com.sunshard.conveyor.model.enums.MaritalStatus;
import com.sunshard.conveyor.model.validation.BirthDate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Loaner's scoring data")
public class ScoringDataDto {

    @Schema(
            type = "number",
            example = "300000",
            description = "amount of your loan"
    )
    @Min(10000)
    @NotNull
    private BigDecimal amount;

    @Schema(
            type = "integer",
            example = "18",
            description = "term of your loan"
    )
    @Min(6)
    @NotNull
    private Integer term;

    @Schema(
            type = "string",
            example = "Moses",
            description = "your first name"
    )
    @Size(min = 2, max = 30)
    @NotEmpty
    private String firstName;

    @Schema(
            type = "string",
            example = "Jackson",
            description = "your last name"
    )
    @Size(min = 2, max = 30)
    @NotEmpty
    private String lastName;

    @Schema(
            type = "string",
            example = "Fitzgerald",
            description = "your middle name"
    )
    @Size(min = 2, max = 30)
    private String middleName;

    @Schema(
            type = "enum",
            example = "MALE",
            description = "loaner's gender"
    )
    @NotNull
    private Gender gender;

    @Schema(
            type = "string",
            format = "date",
            example = "2000-08-04",
            description = "your birthdate"
    )
    @BirthDate
    @NotNull
    private LocalDate birthDate;

    @Schema(
            type = "string",
            example = "6020",
            description = "your passport series"
    )
    @Digits(integer = 4, fraction = 0)
    @NotEmpty
//    @Pattern(regexp = "^[0-9]{4}$")
    private String passportSeries;

    @Schema(
            type = "string",
            example = "425513",
            description = "your passport number"
    )
    @Digits(integer = 6, fraction = 0)
    @NotEmpty
//    @Pattern(regexp = "^[0-9]{6}$")
    private String passportNumber;

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
    @NotEmpty
    private String passportIssueBranch;

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
            description = "loaner's employment data"
    )
    @NotNull
    private EmploymentDto employment;

    @Schema(
            type = "string",
            example = "98765432100123456789",
            description = "loaner's bank account"
    )
    @NotEmpty
    private String account;

    @Schema(
            type = "boolean",
            example = "true",
            description = "shows whether insurance is enabled"
    )
    @NotNull
    private Boolean isInsuranceEnabled;

    @Schema(
            type = "boolean",
            example = "true",
            description = "shows whether loaner is a salary client"
    )
    @NotNull
    private Boolean isSalaryClient;
}
