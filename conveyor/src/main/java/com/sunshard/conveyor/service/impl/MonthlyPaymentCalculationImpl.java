package com.sunshard.conveyor.service.impl;

import com.sunshard.conveyor.service.MonthlyPaymentCalculationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.sunshard.conveyor.service.Constants.SCALE;

@Service
public class MonthlyPaymentCalculationImpl implements MonthlyPaymentCalculationService {
    @Override
    public BigDecimal calculateMonthlyPayment(BigDecimal totalAmount, Integer term) {
        return totalAmount.divide(BigDecimal.valueOf(term), SCALE);
    }
}
