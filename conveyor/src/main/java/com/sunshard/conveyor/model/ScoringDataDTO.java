package com.sunshard.conveyor.model;

import com.sunshard.conveyor.model.enums.Gender;
import com.sunshard.conveyor.model.enums.MaritalStatus;
import com.sunshard.conveyor.validator.BirthDate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Loaner's scoring data")
public class ScoringDataDTO {

    @Min(10000)
    @Schema(type = "number",
            example = "300000",
            description = "amount of your loan"
    )
    private BigDecimal amount;

    @Min(6)
    @Schema(type = "integer",
            example = "18",
            description = "term of your loan"
    )
    private Integer term;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 30)
    @Schema(type = "string",
            example = "Moses",
            description = "your first name"
    )
    private String firstName;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 30)
    @Schema(type = "string",
            example = "Jackson",
            description = "your last name"
    )
    private String lastName;

    @Size(min = 2, max = 30)
    @Schema(type = "string",
            example = "Fitzgerald",
            description = "your middle name"
    )
    private String middleName;

    @Schema(type = "enum",
            example = "MALE",
            description = "loaner's gender"
    )
    private Gender gender;

    @NotNull
    @BirthDate
    @Schema(type = "string",
            format = "date",
            example = "2000-08-04",
            description = "your birthdate"
    )
    private LocalDate birthDate;

    @NotNull
    @Pattern(regexp = "^[0-9]{4}$")
    @Schema(type = "string",
            pattern = "^[0-9]{4}$",
            example = "6020",
            description = "your passport series"
    )
    private String passportSeries;

    @NotNull
    @Pattern(regexp = "^[0-9]{6}$")
    @Schema(type = "string",
            pattern = "^[0-9]{6}$",
            example = "425513",
            description = "your passport number"
    )
    private String passportNumber;

    @Schema(type = "string",
            format = "date",
            example = "2020-09-12",
            description = "loaner's passport issue date"
    )
    private LocalDate passportIssueDate;

    @Schema(type = "string",
            example = "Issue branch number 5",
            description = "loaner's passport issue branch"
    )
    private String passportIssueBranch;

    @Schema(type = "enum",
            example = "NOT_MARRIED",
            description = "loaner's marital status"
    )
    private MaritalStatus maritalStatus;

    @Schema(type = "integer",
            example = "3",
            description = "loaner's amount of dependents"
    )
    private Integer dependentAmount;

    @Schema(
            description = "loaner's employment data"
    )
    private EmploymentDTO employment;

    @Schema(type = "string",
            example = "98765432100123456789",
            description = "loaner's bank account"
    )
    private String account;

    @Schema(type = "boolean",
            example = "true",
            description = "shows whether insurance is enabled"
    )
    private Boolean isInsuranceEnabled;

    @Schema(type = "boolean",
            example = "true",
            description = "shows whether loaner is a salary client"
    )
    private Boolean isSalaryClient;
}
