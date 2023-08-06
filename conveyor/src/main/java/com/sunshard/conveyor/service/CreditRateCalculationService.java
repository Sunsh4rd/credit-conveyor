package com.sunshard.conveyor.service;

import com.sunshard.conveyor.model.ScoringDataDTO;

import java.math.BigDecimal;

public interface CreditRateCalculationService {
    BigDecimal calculateCreditRate(
            BigDecimal currentRate,
            ScoringDataDTO scoringData
    );
}
