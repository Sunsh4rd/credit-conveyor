package com.sunshard.gateway.service;

import com.sunshard.gateway.model.LoanApplicationRequestDTO;
import com.sunshard.gateway.model.LoanOfferDTO;

import java.util.List;

public interface ApplicationService {

    List<LoanOfferDTO> createLoanOffers(LoanApplicationRequestDTO request);

    void applyLoanOffer(LoanOfferDTO loanOffer);
}
