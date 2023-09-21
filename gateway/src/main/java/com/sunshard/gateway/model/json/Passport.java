package com.sunshard.gateway.model.json;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@Schema(description = "loaner passport data")
public class Passport {

    @Schema(
            type = "string",
            example = "6020",
            description = "your passport series"
    )
    private String series;

    @Schema(
            type = "string",
            example = "425513",
            description = "your passport number"
    )
    private String number;

    @Schema(
            type = "string",
            example = "Issue branch number 5",
            description = "loaner's passport issue branch"
    )
    private String issueBranch;

    @Schema(
            type = "string",
            format = "date",
            example = "2020-09-12",
            description = "loaner's passport issue date"
    )
    private LocalDate issueDate;
}
