package com.sunshard.conveyor.service.impl;

import com.sunshard.conveyor.model.LoanApplicationRequestDTO;
import com.sunshard.conveyor.model.LoanOfferDTO;
import com.sunshard.conveyor.service.OfferService;
import com.sunshard.conveyor.service.ScoringService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for creating possible <i>loan offers</i> based on received <i>loan application request</i>
 */
@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final ScoringService scoringService;
    private static final Logger logger = LogManager.getLogger(OfferServiceImpl.class.getName());

    /**
     * Create a list of <i>loan offers</i> based on various  values of <i>isInsuranceEnabled</i> and <i>isSalaryClient</i>
     * @param request loan application request
     * @return list of possible loan offers
     * @see LoanApplicationRequestDTO
     * @see LoanOfferDTO
     */
    @Override
    public List<LoanOfferDTO> createLoanOffers(LoanApplicationRequestDTO request) {
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
     * @see LoanApplicationRequestDTO
     * @see LoanOfferDTO
     */
    private LoanOfferDTO createLoanOffer(
            Boolean isInsuranceEnabled,
            Boolean isSalaryClient,
            LoanApplicationRequestDTO request
    ) {
        logger.info("Calculating rate for loan offer:");
        BigDecimal rate = scoringService.calculateRate(isInsuranceEnabled, isSalaryClient);
        logger.info("Calculated rate is {}", rate);

        logger.info("Calculating monthly payment for loan offer:");
        BigDecimal monthlyPayment = scoringService.calculateMonthlyPayment(
                scoringService.calculateMonthlyRate(rate),
                scoringService.calculateLoanAmountBasedOnInsuranceStatus(request.getAmount(), isInsuranceEnabled),
                request.getTerm()
        );
        logger.info("Calculated monthly payment is {}", monthlyPayment);

        logger.info("Calculating total amount for loan offer:");
        BigDecimal totalAmount = scoringService.calculateTotalAmount(
                monthlyPayment,
                request.getTerm()
        );
        logger.info("Calculated total amount is {}", totalAmount);

        return LoanOfferDTO.builder()
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
