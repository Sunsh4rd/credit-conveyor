package com.sunshard.conveyor.service.impl;

import com.sunshard.conveyor.model.PaymentScheduleElement;
import com.sunshard.conveyor.service.PskCalculationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.sunshard.conveyor.config.util.Constants.HUNDRED;
import static com.sunshard.conveyor.config.util.Constants.SCALE;

@Service
public class PskCalculationImpl implements PskCalculationService {
    @Override
    public BigDecimal calculatePsk(List<PaymentScheduleElement> paymentSchedule, BigDecimal amount) {
        BigDecimal totalPayment = paymentSchedule.stream()
                .map(PaymentScheduleElement::getTotalPayment)
                .reduce(BigDecimal::add)
                .orElseThrow();

        BigDecimal psk = totalPayment
                .divide(amount, SCALE)
                .subtract(BigDecimal.ONE)
                .multiply(HUNDRED);
        return psk;
    }
}
