package com.sunshard.conveyor.core.service.impl;

import com.sunshard.conveyor.core.service.OfferService;
import com.sunshard.conveyor.core.service.ScoringService;
import com.sunshard.conveyor.model.LoanApplicationRequestDto;
import com.sunshard.conveyor.model.LoanOfferDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for creating possible <i>loan offers</i> based on received <i>loan application request</i>
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class OfferServiceImpl implements OfferService {

    private final ScoringService scoringService;

    /**
     * Create a list of <i>loan offers</i> based on various  values of <i>isInsuranceEnabled</i> and <i>isSalaryClient</i>
     * @param request loan application request
     * @return list of possible loan offers
     * @see LoanApplicationRequestDto
     * @see LoanOfferDto
     */
    @Override
    public List<LoanOfferDto> createLoanOffers(LoanApplicationRequestDto request) {
        return new ArrayList<>() {{
            add(createLoanOffer(false, false, request));
            add(createLoanOffer(true, false, request));
            add(createLoanOffer(false, true, request));
            add(createLoanOffer(true, true, request));
        }};
    }

    /**
     * Create specific <i>loan offer</i> based on <i>isInsuranceEnabled</i> and <i>isSalaryClient</i>
     * @param isInsuranceEnabled specifies if insurance is enabled for <i>loan offer</i>
     * @param isSalaryClient specifies if loaner is a salary client
     * @param request loan application request
     * @return specific <i>loan offer</i> based on <i>isInsuranceEnabled</i> and <i>isSalaryClient</i>
     * @see LoanApplicationRequestDto
     * @see LoanOfferDto
     */
    private LoanOfferDto createLoanOffer(
            Boolean isInsuranceEnabled,
            Boolean isSalaryClient,
            LoanApplicationRequestDto request
    ) {
        log.info("Calculating rate for loan offer:");
        BigDecimal rate = scoringService.calculateRate(isInsuranceEnabled, isSalaryClient);
        log.info("Calculated rate is {}", rate);

        log.info("Calculating monthly payment for loan offer:");
        BigDecimal monthlyPayment = scoringService.calculateMonthlyPayment(
                scoringService.calculateMonthlyRate(rate),
                scoringService.calculateLoanAmountBasedOnInsuranceStatus(request.getAmount(), isInsuranceEnabled),
                request.getTerm()
        );
        log.info("Calculated monthly payment is {}", monthlyPayment);

        log.info("Calculating total amount for loan offer:");
        BigDecimal totalAmount = scoringService.calculateTotalAmount(
                monthlyPayment,
                request.getTerm()
        );
        log.info("Calculated total amount is {}", totalAmount);

        return LoanOfferDto.builder()
                .requestedAmount(request.getAmount())
                .rate(rate)
                .monthlyPayment(monthlyPayment)
                .totalAmount(totalAmount)
                .term(request.getTerm())
                .isInsuranceEnabled(isInsuranceEnabled)
                .isSalaryClient(isSalaryClient)
                .build();
    }
}
