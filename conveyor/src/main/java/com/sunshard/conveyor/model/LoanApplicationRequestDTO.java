package com.sunshard.conveyor.model;

import com.sunshard.conveyor.validator.BirthDate;
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

    @NotNull
    @Schema(type = "string",
            pattern = "[\\w\\.]{2,50}@[\\w\\.]{2,20}",
            example = "your@mail.com",
            description = "your email"
    )
    private String email;

    @NotNull
    @BirthDate
    @Schema(type = "string",
            format = "date",
            example = "2004-08-04",
            description = "your birthdate"
    )
    private LocalDate birthdate;

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
}
