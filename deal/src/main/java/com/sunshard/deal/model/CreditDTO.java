package com.sunshard.deal.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Calculated credit data")
public class CreditDTO {

    @Schema(type = "number",
            example = "300000",
            description = "amount of your loan"
    )
    private BigDecimal amount;

    @Schema(type = "integer",
            example = "18",
            description = "term of your loan"
    )
    private Integer term;

    @Schema(type = "number",
            example = "18047.55",
            description = "monthly payment amount"
    )
    private BigDecimal monthlyPayment;

    @Schema(type = "number",
            example = "12.50",
            description = "loan rate"
    )
    private BigDecimal rate;

    @Schema(type = "number",
            example = "448000",
            description = "full loan price"
    )
    private BigDecimal psk;

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

    @Schema(
            description = "list-organized payment schedule data"
    )
    private List<PaymentScheduleElement> paymentSchedule;
}
