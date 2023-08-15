package com.sunshard.deal.service;

import com.sunshard.deal.model.LoanApplicationRequestDTO;
import com.sunshard.deal.model.LoanOfferDTO;
import com.sunshard.deal.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ClientRepository clientRepository;

    public List<LoanOfferDTO> createLoanOffers(LoanApplicationRequestDTO request) {
        return null;
    }
}
