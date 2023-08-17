package com.sunshard.deal.service;

import com.sunshard.deal.model.FinishRegistrationRequestDTO;
import com.sunshard.deal.model.LoanApplicationRequestDTO;
import com.sunshard.deal.model.LoanOfferDTO;

import java.util.List;

public interface DealService {
    List<LoanOfferDTO> createLoanOffers(LoanApplicationRequestDTO request);
    void applyLoanOffer(LoanOfferDTO loanOffer);
    void calculateCreditData(Long applicationId, FinishRegistrationRequestDTO finishRegistrationRequest);

}
