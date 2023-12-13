package com.sunshard.conveyor.model.json;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@Schema(description = "Loaner's passport data")
public class Passport {

    @Schema(
            type = "string",
            example = "6020",
            description = "your passport series"
    )
    @Digits(integer = 4, fraction = 0)
    @NotBlank
    private String series;

    @Schema(
            type = "string",
            example = "425513",
            description = "your passport number"
    )
    @Digits(integer = 6, fraction = 0)
    @NotBlank
    private String number;

    @Schema(
            type = "string",
            example = "Issue branch number 5",
            description = "loaner's passport issue branch"
    )
    @NotBlank
    private String issueBranch;

    @Schema(
            type = "string",
            format = "date",
            example = "2020-09-12",
            description = "loaner's passport issue date"
    )
    @NotNull
    private LocalDate issueDate;
}
