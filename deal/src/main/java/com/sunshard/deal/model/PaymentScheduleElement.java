package com.sunshard.deal.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class PaymentScheduleElement {

    private Integer number;
    private LocalDate date;
    private BigDecimal totalPayment;
    private  BigDecimal interestPayment;
    private BigDecimal debtPayment;
    private  BigDecimal remainingDebt;
}
