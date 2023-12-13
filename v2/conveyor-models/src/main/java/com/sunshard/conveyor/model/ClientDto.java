package com.sunshard.conveyor.model;

import com.sunshard.conveyor.model.enums.Gender;
import com.sunshard.conveyor.model.enums.MaritalStatus;
import com.sunshard.conveyor.model.json.Passport;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@Schema(description = "Client data transfer object")
public class ClientDto {

    @Schema(
            type = "integer",
            format = "int64",
            example = "1",
            description = "client id"
    )
    private Long id;

    @Schema(
            type = "string",
            example = "Moses",
            description = "your first name"
    )
    private String firstName;

    @Schema(
            type = "string",
            example = "Jackson",
            description = "your last name"
    )
    private String lastName;

    @Schema(
            type = "string",
            example = "Fitzgerald",
            description = "your middle name"
    )
    private String middleName;

    @Schema(
            type = "string",
            format = "date",
            example = "2000-08-04",
            description = "your birthdate"
    )
    private LocalDate birthDate;

    @Schema(
            type = "string",
            example = "your@mail.com",
            description = "your email"
    )
    private String email;

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
            example = "98765432100123456789",
            description = "loaner's bank account"
    )
    private String account;

    @Schema(description = "loaner's passport data")
    private Passport passport;

    @Schema(description = "loaner's employment data")
    private EmploymentDto employment;
}
