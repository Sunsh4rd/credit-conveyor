package com.sunshard.gateway.controller;

import com.sunshard.gateway.client.AdminFeignClient;
import com.sunshard.gateway.exception.ApplicationNotFoundException;
import com.sunshard.gateway.model.ApplicationDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController implements AdminAPI {

    private final AdminFeignClient adminFeignClient;

    private static final Logger logger = LogManager.getLogger(AdminController.class.getName());

    @Override
    public ResponseEntity<ApplicationDTO> getApplicationById(Long applicationId) {
        logger.info("Searching for application {}", applicationId);
        ResponseEntity<ApplicationDTO> application = adminFeignClient.getApplicationById(applicationId);
        if (!application.hasBody()) {
            logger.error("No application found for id {}", applicationId);
            throw new ApplicationNotFoundException(applicationId);
        } else {
            logger.info("Found application {}\n{}", applicationId, application.getBody());
            return ResponseEntity.ok(application.getBody());
        }
    }

    @Override
    public ResponseEntity<List<ApplicationDTO>> getAllApplications() {
        logger.info("Requiring all loan applications");
        ResponseEntity<List<ApplicationDTO>> applications = adminFeignClient.getAllApplications();
        if (!applications.hasBody()) {
            logger.error("No applications were found");
            throw new ApplicationNotFoundException();
        } else {
            logger.info("Found applications\n{}", applications.getBody());
            return ResponseEntity.ok(applications.getBody());
        }
    }
}
