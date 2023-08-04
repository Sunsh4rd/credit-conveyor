package com.sunshard.conveyor.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Possible loan offer")
public class LoanOfferDTO {

    @Schema(type = "integer",
            format = "int64",
            example = "1",
            description = "number of the possible offer"
    )
    private Long applicationId;

    @Schema(type = "number",
            example = "300000",
            description = "required loan amount"
    )
    private BigDecimal requestedAmount;

    @Schema(type = "number",
            example = "448000",
            description = "total loan amount"
    )
    private BigDecimal totalAmount;

    @Schema(type = "integer",
            example = "18",
            description = "term of your loan"
    )
    private Integer term;

    @Schema(type = "number",
            example = "18000",
            description = "monthly payment amount"
    )
    private BigDecimal monthlyPayment;

    @Schema(type = "number",
            example = "11",
            description = "loan rate"
    )
    private BigDecimal rate;

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
