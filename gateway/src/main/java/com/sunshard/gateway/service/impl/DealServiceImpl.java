package com.sunshard.gateway.service.impl;

import com.sunshard.gateway.client.DealFeignClient;
import com.sunshard.gateway.model.FinishRegistrationRequestDTO;
import com.sunshard.gateway.service.DealService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final DealFeignClient dealFeignClient;

    private static final Logger logger = LogManager.getLogger(DealServiceImpl.class.getName());

    @Override
    public void calculateCreditData(
            Long applicationId, FinishRegistrationRequestDTO finishRegistrationRequest
    ) {
        logger.info("Calculating credit data for application {}, scoring data:\n{}",
                applicationId, finishRegistrationRequest
        );
        dealFeignClient.calculateCreditData(applicationId, finishRegistrationRequest);
    }

    @Override
    public void sendDocuments(Long applicationId) {
        logger.info("Sending documents for application {}", applicationId);
        dealFeignClient.sendDocuments(applicationId);
    }

    @Override
    public void signRequest(Long applicationId) {
        logger.info("Sending documents for application {}", applicationId);
        dealFeignClient.signRequest(applicationId);
    }

    @Override
    public void signDocuments(Long applicationId, Integer sesCode) {
        logger.info("Signing documents with ses code");
        dealFeignClient.signDocuments(applicationId, sesCode);
    }
}
