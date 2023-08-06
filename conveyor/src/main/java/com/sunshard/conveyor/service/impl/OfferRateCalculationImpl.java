package com.sunshard.conveyor.service.impl;

import com.sunshard.conveyor.service.OfferRateCalculationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.sunshard.conveyor.config.util.Constants.THREE;

@Service
public class OfferRateCalculationImpl implements OfferRateCalculationService {
    @Override
    public BigDecimal calculateRate(
            BigDecimal currentRate,
            Boolean isInsuranceEnabled,
            Boolean isSalaryClient) {
        BigDecimal rate = isInsuranceEnabled ? currentRate.subtract(THREE) : currentRate;
        rate = isSalaryClient ? rate.subtract(BigDecimal.ONE) : rate;
        return rate;
    }
}
