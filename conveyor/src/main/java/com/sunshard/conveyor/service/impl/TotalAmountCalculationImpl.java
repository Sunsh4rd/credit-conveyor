package com.sunshard.conveyor.service.impl;

import com.sunshard.conveyor.service.TotalAmountCalculationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TotalAmountCalculationImpl implements TotalAmountCalculationService {

    @Override
    public BigDecimal calculateTotalAmount(BigDecimal monthlyPayment, Integer term) {
        return monthlyPayment.multiply(BigDecimal.valueOf(term));
    }
}
