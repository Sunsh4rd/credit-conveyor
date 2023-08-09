package com.sunshard.conveyor.service.impl;

import com.sunshard.conveyor.model.LoanApplicationRequestDTO;
import com.sunshard.conveyor.model.LoanOfferDTO;
import com.sunshard.conveyor.service.OfferService;
import com.sunshard.conveyor.service.ScoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Creates possible loan offers based on received loan application request
 */

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final ScoringService scoringService;

//    private final OfferRateCalculationService offerRateCalculationService;
//    private final TotalAmountCalculationService totalAmountCalculationService;
//    private final MonthlyPaymentCalculationService monthlyPaymentCalculationService;
//    private final MonthlyRateCalculationService monthlyRateCalculationService;

    /**
     *
     * @param request
     * @return
     */

    @Override
    public List<LoanOfferDTO> createLoanOffers(LoanApplicationRequestDTO request) {
        List<LoanOfferDTO> loanOffers = new ArrayList<>() {{
            add(createLoanOffer(false, false, request));
            add(createLoanOffer(true, false, request));
            add(createLoanOffer(false, true, request));
            add(createLoanOffer(true, true, request));
        }};
        loanOffers.sort(Comparator.comparing(LoanOfferDTO::getRate));
        return loanOffers;
    }

    private LoanOfferDTO createLoanOffer(
            Boolean isInsuranceEnabled,
            Boolean isSalaryClient,
            LoanApplicationRequestDTO request
    ) {
        BigDecimal rate = scoringService.calculateRate(isInsuranceEnabled, isSalaryClient);

        BigDecimal monthlyPayment = scoringService.calculateMonthlyPayment(
                scoringService.calculateMonthlyRate(rate),
                scoringService.calculateLoanAmountBasedOnInsuranceStatus(request.getAmount(), isInsuranceEnabled),
                request.getTerm()
        );

        BigDecimal totalAmount = scoringService.calculateTotalAmount(
                monthlyPayment,
                request.getTerm()
        );

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
