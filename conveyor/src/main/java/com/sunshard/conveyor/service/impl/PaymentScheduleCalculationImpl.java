package com.sunshard.conveyor.service.impl;

import com.sunshard.conveyor.model.PaymentScheduleElement;
import com.sunshard.conveyor.service.PaymentScheduleCalculationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.sunshard.conveyor.config.util.Constants.HUNDRED;
import static com.sunshard.conveyor.config.util.Constants.SCALE;

@Service
public class PaymentScheduleCalculationImpl implements PaymentScheduleCalculationService {
    @Override
    public List<PaymentScheduleElement> calculatePaymentSchedule(
            BigDecimal amount,
            Integer term,
            BigDecimal rate,
            BigDecimal monthlyPayment
    ) {
        List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();
        BigDecimal remainingDebt = amount;
        for (int i = 0; i < term; i++) {
            LocalDate nextPaymentDate = LocalDate.now().plusMonths(i + 1);
            BigDecimal nextInterestPayment = remainingDebt
                    .multiply(rate).divide(HUNDRED, SCALE)
                    .multiply(BigDecimal.valueOf(nextPaymentDate.lengthOfMonth()))
                    .divide(BigDecimal.valueOf(nextPaymentDate.lengthOfYear()), SCALE);
            BigDecimal nextDebtPayment = monthlyPayment.subtract(nextInterestPayment);
            remainingDebt = remainingDebt.subtract(nextDebtPayment);
            paymentSchedule.add(
                    PaymentScheduleElement.builder()
                            .number(i + 1)
                            .date(nextPaymentDate)
                            .interestPayment(nextInterestPayment)
                            .debtPayment(nextDebtPayment)
                            .totalPayment(monthlyPayment)
                            .remainingDebt(remainingDebt)
                            .build()
            );
        }
        return paymentSchedule;
    }
}
