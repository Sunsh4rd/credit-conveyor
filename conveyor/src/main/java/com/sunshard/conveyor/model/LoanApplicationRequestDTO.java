package com.sunshard.conveyor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
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

    @NotNull
    @Email(regexp = "[\\w\\.]{2,50}@[\\w\\.]{2,20}")
    private String email;

    @NotNull
    private LocalDate birthdate;

    @NotNull
    @Pattern(regexp = "^[0-9]{4}$")
    private String passportSeries;

    @NotNull
    @Pattern(regexp = "^[0-9]{6}")
    private String passportNumber;
}
