package com.sunshard.conveyor.core.service.impl;

import com.sunshard.conveyor.core.service.CalculationService;
import com.sunshard.conveyor.core.service.ScoringService;
import com.sunshard.conveyor.model.CreditDto;
import com.sunshard.conveyor.model.PaymentScheduleElement;
import com.sunshard.conveyor.model.ScoringDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service for calculating <i>credit data</i> based on received <i>scoring data</i>
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class CalculationServiceImpl implements CalculationService {

    private final ScoringService scoringService;

    /**
     * Returns calculated <i>credit data</i> based on <i>scoring details</i>
     * @param scoringData scoring data for credit calculation
     * @return calculated credit data
     * @see ScoringDataDto
     * @see CreditDto
     */
    @Override
    public CreditDto calculateCreditData(ScoringDataDto scoringData) {

        scoringService.validateScoringData(
                scoringData.getEmployment(),
                scoringData.getAmount(),
                scoringData.getBirthDate()
        );

        log.info("Calculating credit rate:");
        BigDecimal rate = scoringService.calculateCreditRate(scoringData);
        log.info("Calculated credit rate: {}", rate);

        log.info("Calculating credit monthly payment:");
        BigDecimal monthlyPayment = scoringService.calculateMonthlyPayment(
                scoringService.calculateMonthlyRate(rate),
                scoringData.getAmount(),
                scoringData.getTerm()
        );
        log.info("Calculated credit monthly payment: {}", monthlyPayment);

        log.info("Calculating credit payment schedule:");
        List<PaymentScheduleElement> paymentSchedule = scoringService.calculatePaymentSchedule(
                scoringData.getAmount(),
                scoringData.getTerm(),
                rate,
                monthlyPayment
        );
        log.info("Calculated credit payment schedule: {}", paymentSchedule);

        log.info("Calculating credit psk:");
        BigDecimal psk = scoringService.calculatePsk(paymentSchedule, scoringData.getAmount());
        log.info("Calculated credit psk: {}", psk);

        return CreditDto.builder()
                .amount(scoringData.getAmount())
                .term(scoringData.getTerm())
                .isInsuranceEnabled(scoringData.getIsInsuranceEnabled())
                .isSalaryClient(scoringData.getIsSalaryClient())
                .rate(rate)
                .monthlyPayment(monthlyPayment)
                .paymentSchedule(paymentSchedule)
                .psk(psk)
                .build();
    }
}
