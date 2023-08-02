package com.sunshard.conveyor.service;

import com.sunshard.conveyor.model.LoanApplicationRequestDTO;
import com.sunshard.conveyor.model.LoanOfferDTO;

import java.util.List;

public interface OfferService {
    List<LoanOfferDTO> createLoanOffers(LoanApplicationRequestDTO loanApplicationRequest);
}
