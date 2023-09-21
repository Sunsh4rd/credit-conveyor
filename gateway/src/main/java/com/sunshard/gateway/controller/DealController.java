package com.sunshard.gateway.controller;

import com.sunshard.gateway.model.FinishRegistrationRequestDTO;
import com.sunshard.gateway.service.DealService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DealController implements DealAPI {

    private final DealService dealService;

    private static final Logger logger = LogManager.getLogger(DealController.class.getName());

    @Override
    public ResponseEntity<Void> calculateCreditData(
            Long applicationId,
            FinishRegistrationRequestDTO finishRegistrationRequest
    ) {
        logger.info("Calculating credit data for application {}, scoring data:\n{}",
                applicationId, finishRegistrationRequest
        );
        dealService.calculateCreditData(applicationId, finishRegistrationRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> sendDocuments(Long applicationId) {
        logger.info("Sending documents for application {}", applicationId);
        dealService.sendDocuments(applicationId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> signRequest(Long applicationId) {
        logger.info("Sending sign documents request for application {}", applicationId);
        dealService.signRequest(applicationId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> signDocuments(Long applicationId, Integer sesCode) {
        logger.info("Signing documents with ses code");
        dealService.signDocuments(applicationId, sesCode);
        return ResponseEntity.ok().build();
    }
}
