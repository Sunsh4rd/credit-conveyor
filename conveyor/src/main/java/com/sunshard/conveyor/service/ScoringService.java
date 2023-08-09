package com.sunshard.conveyor.service;

import com.sunshard.conveyor.exception.CreditDeniedException;
import com.sunshard.conveyor.model.EmploymentDTO;
import com.sunshard.conveyor.model.PaymentScheduleElement;
import com.sunshard.conveyor.model.ScoringDataDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ScoringService {
    BigDecimal calculateRate(Boolean isInsuranceEnabled, Boolean isSalaryClient);
    BigDecimal calculateMonthlyRate(BigDecimal rate);
    BigDecimal calculateMonthlyPayment(BigDecimal monthlyRate, BigDecimal totalAmount, Integer term);
    BigDecimal calculateLoanAmountBasedOnInsuranceStatus(BigDecimal amount, Boolean isInsuranceEnabled);
    BigDecimal calculateTotalAmount(BigDecimal monthlyPayment, Integer term);
    void validateScoringData(
            EmploymentDTO employmentData,
            BigDecimal amount,
            LocalDate birthDate
    ) throws CreditDeniedException;
    BigDecimal calculateCreditRate(ScoringDataDTO scoringData);
    List<PaymentScheduleElement> calculatePaymentSchedule(
            BigDecimal amount,
            Integer term,
            BigDecimal rate,
            BigDecimal monthlyPayment
    );
    BigDecimal calculatePsk(List<PaymentScheduleElement> paymentSchedule, BigDecimal amount);
}
