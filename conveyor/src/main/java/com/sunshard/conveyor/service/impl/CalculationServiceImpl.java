package com.sunshard.conveyor.service.impl;

import com.sunshard.conveyor.exception.CreditDeniedException;
import com.sunshard.conveyor.model.CreditDTO;
import com.sunshard.conveyor.model.PaymentScheduleElement;
import com.sunshard.conveyor.model.ScoringDataDTO;
import com.sunshard.conveyor.model.enums.EmploymentStatus;
import com.sunshard.conveyor.service.CalculationService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class CalculationServiceImpl implements CalculationService {

    private final BigDecimal basicRate = BigDecimal.valueOf(15);
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
                .build();

        List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();
        for (int i = 0; i < scoringData.getTerm(); i++) {
            paymentSchedule.add(
                    PaymentScheduleElement.builder()
                            .number(i + 1)
                            .date(LocalDate.now().plusMonths(i))

                            .build()
            );
        }
        credit.setPaymentSchedule(paymentSchedule);
        return credit;
    }
}
