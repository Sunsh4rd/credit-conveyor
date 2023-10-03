package com.sunshard.application.service;

import com.sunshard.application.model.LoanApplicationRequestDTO;
import com.sunshard.application.model.LoanOfferDTO;

import java.util.List;

public interface ApplicationService {

    List<LoanOfferDTO> createLoanOffers(LoanApplicationRequestDTO request);

    void applyLoanOffer(LoanOfferDTO loanOffer);
}
