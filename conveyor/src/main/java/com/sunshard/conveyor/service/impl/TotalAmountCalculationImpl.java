package com.sunshard.conveyor.service.impl;

import com.sunshard.conveyor.service.TotalAmountCalculationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.sunshard.conveyor.service.Constants.HUNDRED;
import static com.sunshard.conveyor.service.Constants.SCALE;

@Service
public class TotalAmountCalculationImpl implements TotalAmountCalculationService {
    @Override
    public BigDecimal calculateTotalAmount(
            BigDecimal requestedAmount,
            Boolean isInsuranceEnabled,
            BigDecimal insurancePrice,
            BigDecimal rate
    ) {
        return isInsuranceEnabled
                ? requestedAmount
                .add(insurancePrice)
                .multiply(HUNDRED.add(rate)).divide(HUNDRED, SCALE)
                : requestedAmount
                .multiply(HUNDRED.add(rate)).divide(HUNDRED, SCALE);
    }
}
