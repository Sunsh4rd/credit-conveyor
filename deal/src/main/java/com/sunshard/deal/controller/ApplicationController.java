package com.sunshard.deal.controller;

import com.sunshard.deal.client.CreditConveyorFeignClient;
import com.sunshard.deal.entity.Application;
import com.sunshard.deal.entity.Client;
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

    private final CreditConveyorFeignClient creditConveyorFeignClient;
    private final ApplicationService applicationService;

    @Override
    public ResponseEntity<List<LoanOfferDTO>> createLoanOffers(LoanApplicationRequestDTO request) {
        Client client = applicationService.addClient(request);
        List<LoanOfferDTO> loanOffers = creditConveyorFeignClient.createLoanOffers(request);
        Application application = applicationService.createApplicationForClient(client);
        loanOffers.forEach(offer -> offer.setApplicationId(application.getApplicationId()));
        return ResponseEntity.ok(loanOffers);
    }
}
