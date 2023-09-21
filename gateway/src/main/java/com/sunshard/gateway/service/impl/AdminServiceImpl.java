package com.sunshard.gateway.service.impl;

import com.sunshard.gateway.client.AdminFeignClient;
import com.sunshard.gateway.model.ApplicationDTO;
import com.sunshard.gateway.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminFeignClient adminFeignClient;

    private static final Logger logger = LogManager.getLogger(AdminServiceImpl.class.getName());

    @Override
    public ApplicationDTO getApplicationById(Long applicationId) {
        logger.info("Searching for application {}", applicationId);
        return adminFeignClient.getApplicationById(applicationId).getBody();
    }

    @Override
    public List<ApplicationDTO> getAllApplications() {
        logger.info("Requesting all applications");
        return adminFeignClient.getAllApplications().getBody();
    }
}
