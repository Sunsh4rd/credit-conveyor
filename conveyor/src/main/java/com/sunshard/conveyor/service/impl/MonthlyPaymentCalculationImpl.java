package com.sunshard.conveyor.service.impl;

import com.sunshard.conveyor.service.MonthlyPaymentCalculationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.sunshard.conveyor.config.util.Constants.SCALE;

@Service
public class MonthlyPaymentCalculationImpl implements MonthlyPaymentCalculationService {
    @Override
    public BigDecimal calculateMonthlyPayment(BigDecimal rate, BigDecimal totalAmount, Integer term) {
        BigDecimal numerator = rate.multiply(BigDecimal.ONE.add(rate).pow(term));
        BigDecimal denominator = BigDecimal.ONE.add(rate).pow(term).subtract(BigDecimal.ONE);

        BigDecimal annuityCoefficient = numerator.divide(denominator, SCALE);
        BigDecimal monthlyPayment = annuityCoefficient.multiply(totalAmount);
        return monthlyPayment;
    }
}
