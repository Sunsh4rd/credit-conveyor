package com.sunshard.conveyor.service;

import com.sunshard.conveyor.model.LoanApplicationRequestDTO;
import com.sunshard.conveyor.model.LoanOfferDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConveyorService {
    public List<LoanOfferDTO> createLoanOffers(LoanApplicationRequestDTO loanApplicationRequestDto) {
        return List.of(
                new LoanOfferDTO(),
                new LoanOfferDTO(),
                new LoanOfferDTO()
        );
    }

//    private Boolean prescore(LoanApplicationRequestDTO loanApplicationRequestDTO) {
//
//    }

    private LoanOfferDTO createLoanOffer(Boolean isInsuranceEnabled,
                                         Boolean isSalaryClient,
                                         LoanApplicationRequestDTO loanApplicationRequestDTO) {
        return LoanOfferDTO.builder().build();
    }
}
