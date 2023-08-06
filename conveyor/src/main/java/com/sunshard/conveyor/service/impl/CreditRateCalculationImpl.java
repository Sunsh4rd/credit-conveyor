package com.sunshard.conveyor.service.impl;

import com.sunshard.conveyor.exception.CreditDeniedException;
import com.sunshard.conveyor.model.ScoringDataDTO;
import com.sunshard.conveyor.service.CreditRateCalculationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.sunshard.conveyor.config.util.Constants.*;

@Service
public class CreditRateCalculationImpl implements CreditRateCalculationService {

    @Override
    public BigDecimal calculateCreditRate(BigDecimal currentRate, ScoringDataDTO scoringData) {

        BigDecimal rate = new BigDecimal(currentRate.toString());

        switch (scoringData.getEmployment().getEmploymentStatus()) {
            case UNEMPLOYED:
                throw new CreditDeniedException();
            case SELF_EMPLOYED:
                rate = rate.add(BigDecimal.ONE);
                break;
            case BUSINESS_OWNER:
                rate = rate.add(THREE);
                break;
        }

        switch (scoringData.getEmployment().getPosition()) {
            case MIDDLE_MANAGER:
                rate = rate.subtract(TWO);
                break;
            case TOP_MANAGER:
                rate = rate.subtract(FOUR);
                break;
        }

        if (scoringData.getAmount().compareTo(
                scoringData.getEmployment().getSalary().multiply(TWENTY)) > 0) {
            throw new CreditDeniedException();
        }

        switch (scoringData.getMaritalStatus()) {
            case MARRIED:
                rate = rate.subtract(THREE);
                break;
            case NOT_MARRIED:
                break;
            case DIVORCED:
                rate = rate.add(BigDecimal.ONE);
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

        if (scoringData.getEmployment().getWorkExperienceTotal() < 12 ||
                scoringData.getEmployment().getWorkExperienceCurrent() < 3) {
            throw new CreditDeniedException();
        }

        return rate;
    }
}
