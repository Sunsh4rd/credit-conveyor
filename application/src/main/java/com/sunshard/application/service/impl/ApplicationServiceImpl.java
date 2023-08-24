package com.sunshard.application.service.impl;

import com.sunshard.application.client.DealFeignClient;
import com.sunshard.application.model.LoanApplicationRequestDTO;
import com.sunshard.application.model.LoanOfferDTO;
import com.sunshard.application.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final DealFeignClient dealFeignClient;

    private static final Logger logger = LogManager.getLogger(ApplicationServiceImpl.class);


    @Override
    public List<LoanOfferDTO> createLoanOffers(LoanApplicationRequestDTO request) {
        ResponseEntity<List<LoanOfferDTO>> loanOffers = dealFeignClient.createLoanOffers(request);
        List<LoanOfferDTO> resultLoanOffers = null;
        if (loanOffers != null) {
            resultLoanOffers = loanOffers.getBody();
            logger.info("Received loan offers:\n{}", resultLoanOffers);
        }
        else {
            logger.error("Could not get loan offers");
        }
        return resultLoanOffers;
    }

    @Override
    public void applyLoanOffer(LoanOfferDTO loanOffer) {
        dealFeignClient.applyLoanOffer(loanOffer);
    }
}
