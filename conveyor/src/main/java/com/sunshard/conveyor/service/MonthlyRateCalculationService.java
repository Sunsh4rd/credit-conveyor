package com.sunshard.conveyor.service;

import java.math.BigDecimal;

public interface MonthlyRateCalculationService {
    BigDecimal calculateMonthlyRate(BigDecimal rate);
}
