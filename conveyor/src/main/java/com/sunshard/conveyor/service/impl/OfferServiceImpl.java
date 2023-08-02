package com.sunshard.conveyor.service.impl;

import com.sunshard.conveyor.model.LoanApplicationRequestDTO;
import com.sunshard.conveyor.model.LoanOfferDTO;
import com.sunshard.conveyor.service.OfferService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    private final BigDecimal basicRate = BigDecimal.valueOf(15);
    private final BigDecimal insurancePrice = BigDecimal.valueOf(100000);

    @Override
    public List<LoanOfferDTO> createLoanOffers(LoanApplicationRequestDTO loanApplicationRequest) {
        List<LoanOfferDTO> loanOffers = new ArrayList<>(){{
            add(createLoanOffer(false, false, loanApplicationRequest));
            add(createLoanOffer(true, false, loanApplicationRequest));
            add(createLoanOffer(false, true, loanApplicationRequest));
            add(createLoanOffer(true, true, loanApplicationRequest));
        }};
        loanOffers.sort(Comparator.comparing(LoanOfferDTO::getRate));
        return loanOffers;
    }
    //    private Boolean prescore(LoanApplicationRequestDTO loanApplicationRequestDTO) {
//
//    }

    private LoanOfferDTO createLoanOffer(Boolean isInsuranceEnabled,
                                         Boolean isSalaryClient,
                                         LoanApplicationRequestDTO loanApplicationRequest
    ) {
        LoanOfferDTO loanOfferDTO = LoanOfferDTO.builder()
                .requestedAmount(loanApplicationRequest.getAmount())
                .term(loanApplicationRequest.getTerm())
                .isInsuranceEnabled(isInsuranceEnabled)
                .isSalaryClient(isSalaryClient)
                .build();

        BigDecimal rate = isInsuranceEnabled ? basicRate.subtract(BigDecimal.valueOf(3)) : basicRate;
        rate = isSalaryClient ? rate.subtract(BigDecimal.ONE) : rate;
        loanOfferDTO.setRate(rate);

        BigDecimal totalAmount = isInsuranceEnabled
                ? loanOfferDTO.getRequestedAmount()
                .add(insurancePrice)
                .multiply(BigDecimal.valueOf(100).add(loanOfferDTO.getRate())).divide(BigDecimal.valueOf(100))
                : loanOfferDTO.getRequestedAmount()
                .multiply(BigDecimal.valueOf(100).add(loanOfferDTO.getRate())).divide(BigDecimal.valueOf(100));
        loanOfferDTO.setTotalAmount(totalAmount);
        BigDecimal monthlyPayment = totalAmount.divide(BigDecimal.valueOf(loanOfferDTO.getTerm()), 2, RoundingMode.HALF_UP);
        loanOfferDTO.setMonthlyPayment(monthlyPayment);
        return loanOfferDTO;
    }
}
