package com.sunshard.conveyor.model;

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
@Schema(description = "Loan application request")
public class LoanApplicationRequestDTO {

    @Min(10000)
    private BigDecimal amount;

    @Min(6)
    private Integer term;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 30)
    private String firstName;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 30)
    private String lastName;

    @Size(min = 2, max = 30)
    private String middleName;

//    @NotNull
//    @Email(regexp = "[\\w\\.]{2,50}@[\\w\\.]{2,20}")
    @Schema(type = "string", pattern = "[\\w\\.]{2,50}@[\\w\\.]{2,20}", example = "your@mail.com", description = "your email")
    private String email;

    @NotNull
    @BirthDate
    private LocalDate birthdate;

    @NotNull
    @Pattern(regexp = "^[0-9]{4}$")
    private String passportSeries;

    @NotNull
    @Pattern(regexp = "^[0-9]{6}$")
    private String passportNumber;
}
