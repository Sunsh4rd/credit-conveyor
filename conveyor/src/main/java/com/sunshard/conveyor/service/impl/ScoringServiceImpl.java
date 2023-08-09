package com.sunshard.conveyor.service.impl;

import com.sunshard.conveyor.exception.CreditDeniedException;
import com.sunshard.conveyor.model.EmploymentDTO;
import com.sunshard.conveyor.model.PaymentScheduleElement;
import com.sunshard.conveyor.model.ScoringDataDTO;
import com.sunshard.conveyor.model.enums.EmploymentStatus;
import com.sunshard.conveyor.service.ScoringService;
import com.sunshard.conveyor.service.util.CalculationParameters;
import com.sunshard.conveyor.service.util.CreditProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.sunshard.conveyor.service.util.CalculationParameters.*;


@Service
@RequiredArgsConstructor
public class ScoringServiceImpl implements ScoringService {

    private final CreditProperties creditProperties;
    @Override
    public BigDecimal calculateRate(Boolean isInsuranceEnabled, Boolean isSalaryClient) {
        BigDecimal currentRate = creditProperties.getBasicRate();
        BigDecimal rate = isInsuranceEnabled ? currentRate.subtract(THREE) : currentRate;
        return isSalaryClient ? rate.subtract(BigDecimal.ONE) : rate;
    }

    @Override
    public BigDecimal calculateMonthlyRate(BigDecimal rate) {
        return rate.divide(CalculationParameters.HUNDRED.multiply(TWELVE), SCALE);
    }

    @Override
    public BigDecimal calculateMonthlyPayment(BigDecimal monthlyRate, BigDecimal totalAmount, Integer term) {
        BigDecimal numerator = monthlyRate.multiply(BigDecimal.ONE.add(monthlyRate).pow(term));
        BigDecimal denominator = BigDecimal.ONE.add(monthlyRate).pow(term).subtract(BigDecimal.ONE);

        BigDecimal annuityCoefficient = numerator.divide(denominator, SCALE);
        return annuityCoefficient.multiply(totalAmount);
    }

    @Override
    public BigDecimal calculateLoanAmountBasedOnInsuranceStatus(BigDecimal amount, Boolean isInsuranceEnabled) {
        return isInsuranceEnabled ? amount.add(creditProperties.getInsurancePrice()) : amount;
    }

    @Override
    public BigDecimal calculateTotalAmount(BigDecimal monthlyPayment, Integer term) {
        return monthlyPayment.multiply(BigDecimal.valueOf(term));
    }

    @Override
    public void validateScoringData(EmploymentDTO employmentData,
                                    BigDecimal amount,
                                    LocalDate birthDate
    ) throws CreditDeniedException {
        List<String> invalidDataDescription = new ArrayList<>();

        if (employmentData.getEmploymentStatus().equals(EmploymentStatus.UNEMPLOYED)) {
            invalidDataDescription.add("Unable to apply for the loan because of the employment status (UNEMPLOYED)");
        }

        if (amount.compareTo(employmentData.getSalary().multiply(TWENTY)) > 0) {
            invalidDataDescription.add("Unable to apply for the loan because the amount is too large");
        }

        if (ChronoUnit.YEARS.between(birthDate, LocalDate.now()) < 20) {
            invalidDataDescription.add("Unable to apply for the loan because of the age (less than 20)");
        } else if (ChronoUnit.YEARS.between(birthDate, LocalDate.now()) > 60) {
            invalidDataDescription.add("Unable to apply for the loan because of the age (more than 60)");
        }

        if (employmentData.getWorkExperienceTotal() < 12 ||
                employmentData.getWorkExperienceCurrent() < 3) {
            invalidDataDescription.add("Unable to apply for the loan because the work experience is too low");
        }

        if (!invalidDataDescription.isEmpty()) {
            throw new CreditDeniedException(invalidDataDescription.get(0));
        }
    }

    @Override
    public BigDecimal calculateCreditRate(ScoringDataDTO scoringData) {
        BigDecimal rate = new BigDecimal(creditProperties.getBasicRate().toString());

        switch (scoringData.getEmployment().getEmploymentStatus()) {
            case SELF_EMPLOYED:
            case EMPLOYED:
                rate = rate.add(BigDecimal.ONE);
                break;
            case BUSINESS_OWNER:
                rate = rate.add(THREE);
                break;
        }

        switch (scoringData.getEmployment().getPosition()) {
            case WORKER:
            case MIDDLE_MANAGER:
                rate = rate.subtract(TWO);
                break;
            case TOP_MANAGER:
                rate = rate.subtract(FOUR);
                break;
            case OWNER:
                rate = rate.subtract(THREE);
        }

        switch (scoringData.getMaritalStatus()) {
            case MARRIED:
                rate = rate.subtract(THREE);
                break;
            case SINGLE:
                break;
            case DIVORCED:
                rate = rate.add(BigDecimal.ONE);
                break;
            case WIDOW_WIDOWER:
                rate = rate.subtract(TWO);
        }

        if (scoringData.getDependentAmount() > 1) {
            rate = rate.add(BigDecimal.ONE);
        }

        switch (scoringData.getGender()) {
            case FEMALE:
                if (ChronoUnit.YEARS.between(scoringData.getBirthDate(), LocalDate.now()) >= 35 ||
                        ChronoUnit.YEARS.between(scoringData.getBirthDate(), LocalDate.now()) <= 60) {
                    rate = rate.subtract(THREE);
                }
                break;
            case MALE:
                if (ChronoUnit.YEARS.between(scoringData.getBirthDate(), LocalDate.now()) >= 30 ||
                        ChronoUnit.YEARS.between(scoringData.getBirthDate(), LocalDate.now()) <= 55) {
                    rate = rate.subtract(THREE);
                }
                break;
            case NON_BINARY:
                rate = rate.add(THREE);
                break;
        }
        return rate;
    }

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

    @Override
    public BigDecimal calculatePsk(List<PaymentScheduleElement> paymentSchedule, BigDecimal amount) {
        BigDecimal totalPayment = paymentSchedule.stream()
                .map(PaymentScheduleElement::getTotalPayment)
                .reduce(BigDecimal::add)
                .orElseThrow();

        return totalPayment
                .divide(amount, SCALE)
                .subtract(BigDecimal.ONE)
                .multiply(HUNDRED);
    }
}
