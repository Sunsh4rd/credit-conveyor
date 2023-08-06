package com.sunshard.conveyor.service;

import java.math.BigDecimal;

public interface TotalAmountCalculationService {
    BigDecimal calculateTotalAmount(
            BigDecimal monthlyPayment,
            Integer term
    );
}
