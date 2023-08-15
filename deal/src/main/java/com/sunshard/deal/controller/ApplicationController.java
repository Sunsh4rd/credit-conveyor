package com.sunshard.deal.controller;

import com.sunshard.deal.client.OfferFeignClient;
import com.sunshard.deal.model.LoanApplicationRequestDTO;
import com.sunshard.deal.model.LoanOfferDTO;
import com.sunshard.deal.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicationController implements ApplicationAPI {

//    private final ApplicationService applicationService;
    private final OfferFeignClient offerFeignClient;

    @Override
    public ResponseEntity<List<LoanOfferDTO>> createLoanOffers(LoanApplicationRequestDTO request) {
        return offerFeignClient.createLoanOffers(request);
    }
}
