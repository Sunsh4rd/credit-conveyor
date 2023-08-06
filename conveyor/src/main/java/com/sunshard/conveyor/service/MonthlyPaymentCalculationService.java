package com.sunshard.conveyor.service;

import java.math.BigDecimal;

public interface MonthlyPaymentCalculationService {
    BigDecimal calculateMonthlyPayment(
            BigDecimal totalAmount,
            Integer term
    );
}
