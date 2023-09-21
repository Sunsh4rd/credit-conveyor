package com.sunshard.gateway.service.impl;

import com.sunshard.gateway.client.ApplicationFeignClient;
import com.sunshard.gateway.model.LoanApplicationRequestDTO;
import com.sunshard.gateway.model.LoanOfferDTO;
import com.sunshard.gateway.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationFeignClient applicationFeignClient;

    private static final Logger logger = LogManager.getLogger(ApplicationServiceImpl.class.getName());

    @Override
    public List<LoanOfferDTO> createLoanOffers(LoanApplicationRequestDTO request) {
        logger.info("Creating loan offers for application request\n{}", request);
        return applicationFeignClient.createLoanOffers(request).getBody();
    }

    @Override
    public void applyLoanOffer(LoanOfferDTO loanOffer) {
        logger.info("Applying loan offer\n{}", loanOffer);
        applicationFeignClient.applyLoanOffer(loanOffer);
    }
}
