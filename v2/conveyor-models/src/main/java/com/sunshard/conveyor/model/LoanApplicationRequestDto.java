package com.sunshard.conveyor.model;

import com.sunshard.conveyor.model.validation.BirthDate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@Schema(description = "Loan application request")
public class LoanApplicationRequestDto {

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
    @NotBlank
    private String firstName;

    @Schema(
            type = "string",
            example = "Jackson",
            description = "your last name"
    )
    @Size(min = 2, max = 30)
    @NotBlank
    private String lastName;

    @Schema(
            type = "string",
            example = "Fitzgerald",
            description = "your middle name"
    )
    @Size(min = 2, max = 30)
    private String middleName;

    @Schema(
            type = "string",
            example = "your@mail.com",
            description = "your email"
    )
    @NotBlank
    @Email(regexp = "^[\\w.]{2,50}@[\\w.]{2,20}$")
    private String email;

    @Schema(
            type = "string",
            format = "date",
            example = "2004-08-04",
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
//    @Pattern(regexp = "^[0-9]{4}$")
    @Digits(integer = 4, fraction = 0)
    @NotBlank
    private String passportSeries;

    @Schema(
            type = "string",
            example = "425513",
            description = "your passport number"
    )
//    @Pattern(regexp = "^[0-9]{6}$")
    @Digits(integer = 6, fraction = 0)
    @NotBlank
    private String passportNumber;

}
