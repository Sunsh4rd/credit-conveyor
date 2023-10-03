package com.sunshard.gateway.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Loan application request")
public class LoanApplicationRequestDTO {

    @Min(value = 10000, message = "Minimal loan amount is 10000")
    @Schema(
            type = "number",
            example = "300000",
            description = "amount of your loan"
    )
    private BigDecimal amount;

    @Min(value = 6, message = "Minimal loan term is 18")
    @Schema(
            type = "integer",
            example = "18",
            description = "term of your loan"
    )
    private Integer term;

    @NotBlank
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters long")
    @Schema(
            type = "string",
            example = "Moses",
            description = "your first name"
    )
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 30, message = "Last name should be between 2 and 30 characters long")
    @Schema(
            type = "string",
            example = "Jackson",
            description = "your last name"
    )
    private String lastName;

    @Size(min = 2, max = 30, message = "Middle name should be between 2 and 30 characters long")
    @Schema(
            type = "string",
            example = "Fitzgerald",
            description = "your middle name"
    )
    private String middleName;

    @NotNull
    @Schema(
            type = "string",
            example = "your@mail.com",
            description = "your email"
    )
    @Pattern(regexp = "^[\\w.]{2,50}@[\\w.]{2,20}$", message = "Email should match pattern")
    private String email;

    @NotNull
    @Schema(
            type = "string",
            format = "date",
            example = "2000-08-04",
            description = "your birthdate"
    )
    private LocalDate birthDate;

    @NotBlank
    @Schema(
            type = "string",
            example = "6020",
            description = "your passport series"
    )
    @Pattern(regexp = "^[0-9]{4}$", message = "Passport series should be 4 digits")
    private String passportSeries;

    @NotBlank
    @Schema(
            type = "string",
            example = "425513",
            description = "your passport number"
    )
    @Pattern(regexp = "^[0-9]{6}$", message = "Passport number should be 6 digits")
    private String passportNumber;
}
