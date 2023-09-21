package com.sunshard.deal.controller;

import com.sunshard.deal.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ClientController implements ClientAPI {

    private final ClientService clientService;
    private static final Logger logger = LogManager.getLogger(ClientController.class.getName());

    @Override
    public ResponseEntity<String> getCreditData(Long applicationId) {
        logger.info("Received credit data for application {}", applicationId);
        String creditData = clientService.getCreditData(applicationId);
        return ResponseEntity.ok(creditData);
    }

    @Override
    public ResponseEntity<String> getSesCode(Long applicationId) {
        logger.info("Received ses code for application {}", applicationId);
        String sesCode = clientService.getSesCode(applicationId);
        return ResponseEntity.ok(sesCode);
    }
}
