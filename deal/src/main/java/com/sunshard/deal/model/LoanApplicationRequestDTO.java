package com.sunshard.deal.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Loan application request")
public class LoanApplicationRequestDTO {

    @Schema(
            type = "number",
            example = "300000",
            description = "amount of your loan"
    )
    private BigDecimal amount;

    @Schema(
            type = "integer",
            example = "18",
            description = "term of your loan"
    )
    private Integer term;

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
            example = "your@mail.com",
            description = "your email"
    )
    private String email;

    @Schema(
            type = "string",
            format = "date",
            example = "2000-08-04",
            description = "your birthdate"
    )
    private LocalDate birthDate;

    @Schema(
            type = "string",
            example = "6020",
            description = "your passport series"
    )
    private String passportSeries;

    @Schema(
            type = "string",
            example = "425513",
            description = "your passport number"
    )
    private String passportNumber;
}
