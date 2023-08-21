package com.sunshard.application.service.impl;

import com.sunshard.application.client.DealFeignClient;
import com.sunshard.application.model.LoanApplicationRequestDTO;
import com.sunshard.application.model.LoanOfferDTO;
import com.sunshard.application.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final DealFeignClient dealFeignClient;


    @Override
    public List<LoanOfferDTO> createLoanOffers(LoanApplicationRequestDTO request) {
        return dealFeignClient.createLoanOffers(request).getBody();
    }

    @Override
    public void applyLoanOffer(LoanOfferDTO loanOffer) {
        dealFeignClient.applyLoanOffer(loanOffer);
    }
}
