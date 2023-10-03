package com.sunshard.application.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Single payment element data")
public class PaymentScheduleElement {

    @Schema(
            type = "integer",
            example = "1",
            description = "number of schedule element"
    )
    private Integer number;

    @Schema(
            type = "string",
            format = "date",
            example = "2023-10-05",
            description = "date of payment"
    )
    private LocalDate date;

    @Schema(
            type = "number",
            example = "30000",
            description = "total payment"
    )
    private BigDecimal totalPayment;

    @Schema(
            type = "number",
            example = "5000",
            description = "interest payment"
    )
    private  BigDecimal interestPayment;

    @Schema(
            type = "number",
            example = "25000",
            description = "debt payment"
    )
    private BigDecimal debtPayment;

    @Schema(
            type = "number",
            example = "270000",
            description = "remaining debt"
    )
    private  BigDecimal remainingDebt;
}
