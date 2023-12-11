package com.sunshard.conveyor.core.service;

import com.sunshard.conveyor.model.LoanApplicationRequestDto;
import com.sunshard.conveyor.model.LoanOfferDto;

import java.util.List;

public interface OfferService {

    List<LoanOfferDto> createLoanOffers(LoanApplicationRequestDto request);
    
}
