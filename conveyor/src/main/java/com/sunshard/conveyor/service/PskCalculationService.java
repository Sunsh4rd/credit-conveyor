package com.sunshard.conveyor.service;

import com.sunshard.conveyor.model.PaymentScheduleElement;

import java.math.BigDecimal;
import java.util.List;

public interface PskCalculationService {
    BigDecimal calculatePsk(List<PaymentScheduleElement> paymentSchedule, BigDecimal amount);
}
