package com.sunshard.gateway.controller;

import com.sunshard.gateway.client.ApplicationFeignClient;
import com.sunshard.gateway.exception.LoanOffersNotCreatedException;
import com.sunshard.gateway.model.LoanApplicationRequestDTO;
import com.sunshard.gateway.model.LoanOfferDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicationController implements ApplicationAPI {

    private final ApplicationFeignClient applicationFeignClient;

    private static final Logger logger = LogManager.getLogger(ApplicationController.class.getName());

    @Override
    public ResponseEntity<List<LoanOfferDTO>> createLoanOffers(LoanApplicationRequestDTO request) {
        logger.info("Creating loan offers for application request\n{}", request);
        ResponseEntity<List<LoanOfferDTO>> loanOffers = applicationFeignClient.createLoanOffers(request);
        if (!loanOffers.hasBody()) {
            logger.error("Loan offers were not created");
            throw new LoanOffersNotCreatedException("Loan offers were not created");
        } else {
            logger.info("Created loan offers\n{}", loanOffers.getBody());
            return ResponseEntity.ok(loanOffers.getBody());
        }
    }

    @Override
    public ResponseEntity<Void> applyLoanOffer(LoanOfferDTO loanOffer) {
        logger.info("Applying loan offer\n{}", loanOffer);
        return applicationFeignClient.applyLoanOffer(loanOffer);
    }
}
