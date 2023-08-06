package com.sunshard.conveyor.service.impl;

import com.sunshard.conveyor.service.MonthlyRateCalculationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.sunshard.conveyor.config.util.Constants.*;

@Service
public class MonthlyRateCalculationImpl implements MonthlyRateCalculationService {
    @Override
    public BigDecimal calculateMonthlyRate(BigDecimal rate) {
        return rate.divide(HUNDRED.multiply(TWELVE), SCALE
        );
    }
}
