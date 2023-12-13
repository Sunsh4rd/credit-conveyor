package com.sunshard.conveyor.deal.service;

import com.sunshard.conveyor.model.FinishRegistrationRequestDto;
import com.sunshard.conveyor.model.LoanApplicationRequestDto;
import com.sunshard.conveyor.model.LoanOfferDto;

import java.util.List;

public interface DealService {

    List<LoanOfferDto> createLoanOffers(LoanApplicationRequestDto request);

    void applyLoanOffer(LoanOfferDto loanOffer);

    void calculateCreditData(Long applicationId, FinishRegistrationRequestDto finishRegistrationRequest);

}
