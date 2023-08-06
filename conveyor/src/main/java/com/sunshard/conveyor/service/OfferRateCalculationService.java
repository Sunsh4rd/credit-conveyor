package com.sunshard.conveyor.service;

import java.math.BigDecimal;

public interface OfferRateCalculationService {

    BigDecimal calculateRate(
            BigDecimal currentRate,
            Boolean isInsuranceEnabled,
            Boolean isSalaryClient
    );
}
