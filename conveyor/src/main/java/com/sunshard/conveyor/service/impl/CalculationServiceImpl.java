package com.sunshard.conveyor.service.impl;

import com.sunshard.conveyor.exception.CreditDeniedException;
import com.sunshard.conveyor.model.CreditDTO;
import com.sunshard.conveyor.model.PaymentScheduleElement;
import com.sunshard.conveyor.model.ScoringDataDTO;
import com.sunshard.conveyor.service.CalculationService;
import com.sunshard.conveyor.service.ScoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 */
@Service
@RequiredArgsConstructor
public class CalculationServiceImpl implements CalculationService {

    private final ScoringService scoringService;

    /**
     *
     * @param scoringData
     * @return CreditDTO
     */
    @Override
    public CreditDTO calculateCreditData(ScoringDataDTO scoringData) {

        scoringService.validateScoringData(
                scoringData.getEmployment(),
                scoringData.getAmount(),
                scoringData.getBirthDate()
        );


        BigDecimal rate = scoringService.calculateCreditRate(scoringData);

        BigDecimal monthlyPayment = scoringService.calculateMonthlyPayment(
                scoringService.calculateMonthlyRate(rate),
                scoringData.getAmount(),
                scoringData.getTerm()
        );

        List<PaymentScheduleElement> paymentSchedule = scoringService.calculatePaymentSchedule(
                scoringData.getAmount(),
                scoringData.getTerm(),
                rate,
                monthlyPayment
        );

        BigDecimal psk = scoringService.calculatePsk(paymentSchedule, scoringData.getAmount());

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
