package com.sunshard.conveyor.model;

import com.sunshard.conveyor.model.enums.CreditStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class InnerCreditDto {

    private Long id;
    private BigDecimal amount;
    private Integer term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private BigDecimal psk;
    private List<PaymentScheduleElement> paymentSchedule;
    private Boolean insuranceEnabled;
    private Boolean salaryClient;
    private CreditStatus creditStatus;

}
