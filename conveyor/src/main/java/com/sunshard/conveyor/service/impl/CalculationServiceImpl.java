package com.sunshard.conveyor.service.impl;

import com.sunshard.conveyor.config.util.CreditProperties;
import com.sunshard.conveyor.model.CreditDTO;
import com.sunshard.conveyor.model.PaymentScheduleElement;
import com.sunshard.conveyor.model.ScoringDataDTO;
import com.sunshard.conveyor.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculationServiceImpl implements CalculationService {

    private final CreditRateCalculationService creditRateCalculationService;
    private final MonthlyRateCalculationService monthlyRateCalculationService;
    private final MonthlyPaymentCalculationService monthlyPaymentCalculationService;
    private final PaymentScheduleCalculationService paymentScheduleCalculationService;
    private final PskCalculationService pskCalculationService;
    private final CreditProperties creditProperties;
    
    @Override
    public CreditDTO calculation(ScoringDataDTO scoringData) {

        BigDecimal rate = creditRateCalculationService.calculateCreditRate(
                creditProperties.getBasicRate(), scoringData
        );

        CreditDTO credit = CreditDTO.builder()
                .amount(scoringData.getAmount())
                .term(scoringData.getTerm())
                .isInsuranceEnabled(scoringData.getIsInsuranceEnabled())
                .isSalaryClient(scoringData.getIsSalaryClient())
                .rate(rate)
                .build();

        BigDecimal monthlyRate = monthlyRateCalculationService.calculateMonthlyRate(rate);
        BigDecimal monthlyPayment = monthlyPaymentCalculationService.calculateMonthlyPayment(
                monthlyRate, scoringData.getAmount(), scoringData.getTerm()
        );
        credit.setMonthlyPayment(monthlyPayment);

        List<PaymentScheduleElement> paymentSchedule = paymentScheduleCalculationService.calculatePaymentSchedule(
                scoringData.getAmount(),
                scoringData.getTerm(),
                rate,
                monthlyPayment
        );
        credit.setPaymentSchedule(paymentSchedule);
        credit.setPsk(pskCalculationService.calculatePsk(paymentSchedule, scoringData.getAmount()));
        return credit;
    }
}
