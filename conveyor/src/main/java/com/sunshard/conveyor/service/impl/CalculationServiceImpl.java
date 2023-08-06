package com.sunshard.conveyor.service.impl;

import com.sunshard.conveyor.exception.CreditDeniedException;
import com.sunshard.conveyor.model.CreditDTO;
import com.sunshard.conveyor.model.PaymentScheduleElement;
import com.sunshard.conveyor.model.ScoringDataDTO;
import com.sunshard.conveyor.model.enums.EmploymentStatus;
import com.sunshard.conveyor.service.CalculationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalculationServiceImpl implements CalculationService {

    @Value("${credit.basic-rate}")
    private BigDecimal basicRate;
    @Override
    public CreditDTO calculation(ScoringDataDTO scoringData) {

        System.out.println(scoringData);

        BigDecimal rate = basicRate;

        switch (scoringData.getEmployment().getEmploymentStatus()) {
            case UNEMPLOYED:
                throw new CreditDeniedException();
            case SELF_EMPLOYED:
                rate = rate.add(BigDecimal.ONE);
                break;
            case BUSINESS_OWNER:
                rate = rate.add(BigDecimal.valueOf(3));
                break;
        }

        switch (scoringData.getEmployment().getPosition()) {
            case MIDDLE_MANAGER:
                rate = rate.subtract(BigDecimal.valueOf(2));
                break;
            case TOP_MANAGER:
                rate = rate.subtract(BigDecimal.valueOf(4));
                break;
        }

        if (scoringData.getAmount().compareTo(
                scoringData.getEmployment().getSalary().multiply(BigDecimal.valueOf(20))) > 0) {
            throw new CreditDeniedException();
        }

        switch (scoringData.getMaritalStatus()) {
            case MARRIED:
                rate = rate.subtract(BigDecimal.valueOf(3));
                break;
            case NOT_MARRIED:
                break;
            case DIVORCED:
                rate = rate.add(BigDecimal.valueOf(1));
                break;
        }

        if (scoringData.getDependentAmount() > 1) {
            rate = rate.add(BigDecimal.ONE);
        }

        if (ChronoUnit.YEARS.between(scoringData.getBirthDate(), LocalDate.now()) < 20 ||
                ChronoUnit.YEARS.between(scoringData.getBirthDate(), LocalDate.now()) > 60) {
            throw new CreditDeniedException();
        }

        switch (scoringData.getGender()) {
            case FEMALE:
                if (ChronoUnit.YEARS.between(scoringData.getBirthDate(), LocalDate.now()) >= 35 ||
                        ChronoUnit.YEARS.between(scoringData.getBirthDate(), LocalDate.now()) <= 60) {
                    rate = rate.subtract(BigDecimal.valueOf(3));
                }
                break;
            case MALE:
                if (ChronoUnit.YEARS.between(scoringData.getBirthDate(), LocalDate.now()) >= 30 ||
                        ChronoUnit.YEARS.between(scoringData.getBirthDate(), LocalDate.now()) <= 55) {
                    rate = rate.subtract(BigDecimal.valueOf(3));
                }
                break;
            case NON_BINARY:
                rate = rate.add(BigDecimal.valueOf(3));
                break;
        }

        if (scoringData.getEmployment().getWorkExperienceTotal() < 12 ||
                scoringData.getEmployment().getWorkExperienceCurrent() < 3) {
            throw new CreditDeniedException();
        }

        CreditDTO credit = CreditDTO.builder()
                .amount(scoringData.getAmount())
                .term(scoringData.getTerm())
                .isInsuranceEnabled(scoringData.getIsInsuranceEnabled())
                .isSalaryClient(scoringData.getIsSalaryClient())
                .rate(rate)
                .build();

        BigDecimal monthlyRate = rate.divide(
                BigDecimal.valueOf(100).multiply(BigDecimal.valueOf(12)), 7, RoundingMode.HALF_UP);

        BigDecimal num = monthlyRate.multiply(BigDecimal.ONE.add(monthlyRate).pow(scoringData.getTerm()));
        BigDecimal denominator = BigDecimal.ONE.add(monthlyRate).pow(scoringData.getTerm()).subtract(BigDecimal.ONE);

        BigDecimal annuityCoefficient = num.divide(denominator, 7, RoundingMode.HALF_UP);
        BigDecimal monthlyPayment = annuityCoefficient.multiply(scoringData.getAmount());

        credit.setMonthlyPayment(monthlyPayment);

        List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();
        BigDecimal remainingDebt = scoringData.getAmount();
        for (int i = 0; i < scoringData.getTerm(); i++) {
            LocalDate nextPaymentDate = LocalDate.now().plusMonths(i + 1);
            BigDecimal nextInterestPayment = remainingDebt
                    .multiply(rate).divide(BigDecimal.valueOf(100), 7, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(nextPaymentDate.lengthOfMonth()))
                    .divide(BigDecimal.valueOf(nextPaymentDate.lengthOfYear()), 7, RoundingMode.HALF_UP);
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
        BigDecimal totalPayment = paymentSchedule.stream()
                .map(PaymentScheduleElement::getTotalPayment)
                .reduce(BigDecimal::add)
                .orElseThrow();
        BigDecimal psk = totalPayment
                .divide(scoringData.getAmount(), 7, RoundingMode.HALF_UP)
                .subtract(BigDecimal.ONE)
                .multiply(BigDecimal.valueOf(100));
        credit.setPsk(psk);
        credit.setPaymentSchedule(paymentSchedule);

        return credit;
    }
}
