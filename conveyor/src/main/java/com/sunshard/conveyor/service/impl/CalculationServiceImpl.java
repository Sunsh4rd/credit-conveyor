package com.sunshard.conveyor.service.impl;

import com.sunshard.conveyor.model.CreditDTO;
import com.sunshard.conveyor.model.PaymentScheduleElement;
import com.sunshard.conveyor.model.ScoringDataDTO;
import com.sunshard.conveyor.service.CalculationService;
import com.sunshard.conveyor.service.ScoringService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service for calculating <i>credit data</i> based on received <i>scoring data</i>
 */
@Service
@RequiredArgsConstructor
public class CalculationServiceImpl implements CalculationService {

    private final ScoringService scoringService;
    private static final Logger logger = LogManager.getLogger(CalculationServiceImpl.class.getName());

    /**
     * Returns calculated <i>credit data</i> based on <i>scoring details</i>
     * @param scoringData scoring data for credit calculation
     * @return calculated credit data
     * @see ScoringDataDTO
     * @see CreditDTO
     */
    @Override
    public CreditDTO calculateCreditData(ScoringDataDTO scoringData) {

        scoringService.validateScoringData(
                scoringData.getEmployment(),
                scoringData.getAmount(),
                scoringData.getBirthDate()
        );

        logger.info("Calculating credit rate:");
        BigDecimal rate = scoringService.calculateCreditRate(scoringData);
        logger.info("Calculated credit rate: {}", rate);

        logger.info("Calculating credit monthly payment:");
        BigDecimal monthlyPayment = scoringService.calculateMonthlyPayment(
                scoringService.calculateMonthlyRate(rate),
                scoringData.getAmount(),
                scoringData.getTerm()
        );
        logger.info("Calculated credit monthly payment: {}", monthlyPayment);

        logger.info("Calculating credit payment schedule:");
        List<PaymentScheduleElement> paymentSchedule = scoringService.calculatePaymentSchedule(
                scoringData.getAmount(),
                scoringData.getTerm(),
                rate,
                monthlyPayment
        );
        logger.info("Calculated credit payment schedule: {}", paymentSchedule);

        logger.info("Calculating credit psk:");
        BigDecimal psk = scoringService.calculatePsk(paymentSchedule, scoringData.getAmount());
        logger.info("Calculated credit psk: {}", psk);

        return CreditDTO.builder()
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
