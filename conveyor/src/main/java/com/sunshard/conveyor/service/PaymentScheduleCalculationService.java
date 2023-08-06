package com.sunshard.conveyor.service;

import com.sunshard.conveyor.model.PaymentScheduleElement;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentScheduleCalculationService {
    List<PaymentScheduleElement> calculatePaymentSchedule(
            BigDecimal amount,
            Integer term,
            BigDecimal rate,
            BigDecimal monthlyPayment
    );
}
