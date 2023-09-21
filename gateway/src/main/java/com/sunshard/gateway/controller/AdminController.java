package com.sunshard.gateway.controller;

import com.sunshard.gateway.model.ApplicationDTO;
import com.sunshard.gateway.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController implements AdminAPI {

    private final AdminService adminService;

    private static final Logger logger = LogManager.getLogger(AdminController.class.getName());

    @Override
    public ResponseEntity<ApplicationDTO> getApplicationById(Long applicationId) {
        logger.info("Searching for application {}", applicationId);
        ApplicationDTO application = adminService.getApplicationById(applicationId);
        logger.info("Found application {}\n{}", applicationId, application);
        return ResponseEntity.ok(application);
    }

    @Override
    public ResponseEntity<List<ApplicationDTO>> getAllApplications() {
        logger.info("Requiring all loan applications");
        List<ApplicationDTO> applications = adminService.getAllApplications();
        logger.info("Found applications\n{}", applications);
        return ResponseEntity.ok(applications);
    }
}
