package com.sunshard.conveyor.service.impl;

import com.sunshard.conveyor.config.util.CreditProperties;
import com.sunshard.conveyor.model.LoanApplicationRequestDTO;
import com.sunshard.conveyor.model.LoanOfferDTO;
import com.sunshard.conveyor.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferRateCalculationService offerRateCalculationService;
    private final TotalAmountCalculationService totalAmountCalculationService;
    private final MonthlyPaymentCalculationService monthlyPaymentCalculationService;
    private final MonthlyRateCalculationService monthlyRateCalculationService;
    private final CreditProperties creditProperties;

    @Override
    public List<LoanOfferDTO> createLoanOffers(LoanApplicationRequestDTO loanApplicationRequest) {
        List<LoanOfferDTO> loanOffers = new ArrayList<>() {{
            add(createLoanOffer(false, false, loanApplicationRequest));
            add(createLoanOffer(true, false, loanApplicationRequest));
            add(createLoanOffer(false, true, loanApplicationRequest));
            add(createLoanOffer(true, true, loanApplicationRequest));
        }};
        loanOffers.sort(Comparator.comparing(LoanOfferDTO::getRate));
        return loanOffers;
    }

    private LoanOfferDTO createLoanOffer(
            Boolean isInsuranceEnabled,
            Boolean isSalaryClient,
            LoanApplicationRequestDTO loanApplicationRequest
    ) {
        LoanOfferDTO loanOffer = LoanOfferDTO.builder()
                .requestedAmount(loanApplicationRequest.getAmount())
                .term(loanApplicationRequest.getTerm())
                .isInsuranceEnabled(isInsuranceEnabled)
                .isSalaryClient(isSalaryClient)
                .build();

        BigDecimal rate = offerRateCalculationService.calculateRate(
                creditProperties.getBasicRate(), isInsuranceEnabled, isSalaryClient
        );
        loanOffer.setRate(rate);

        BigDecimal monthlyPayment = monthlyPaymentCalculationService.calculateMonthlyPayment(
                monthlyRateCalculationService.calculateMonthlyRate(rate),
                loanApplicationRequest.getAmount(),
                loanApplicationRequest.getTerm()
        );
        loanOffer.setMonthlyPayment(monthlyPayment);

        BigDecimal totalAmount = totalAmountCalculationService.calculateTotalAmount(
                monthlyPayment,
                loanApplicationRequest.getTerm()
        );
        loanOffer.setTotalAmount(totalAmount);

        return loanOffer;
    }
}
