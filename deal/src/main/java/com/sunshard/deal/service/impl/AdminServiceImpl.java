package com.sunshard.deal.service.impl;

import com.sunshard.deal.entity.Application;
import com.sunshard.deal.exception.ApplicationNotFoundException;
import com.sunshard.deal.mapper.ApplicationMapper;
import com.sunshard.deal.model.ApplicationDTO;
import com.sunshard.deal.repository.ApplicationRepository;
import com.sunshard.deal.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

    private static final Logger logger = LogManager.getLogger(AdminServiceImpl.class.getName());

    @Override
    public ApplicationDTO getApplicationById(Long applicationId) {
        logger.info("Searching for application {}", applicationId);
        Application application = applicationRepository.findById(applicationId).orElseThrow(
                () -> new ApplicationNotFoundException(applicationId)
        );
        ApplicationDTO applicationDTO = applicationMapper.entityToDto(application);
        logger.info("Found application {}\n{}", applicationId, applicationDTO);
        return applicationDTO;
    }

    @Override
    public List<ApplicationDTO> getAllApplications() {
        logger.info("Requested all applications");
        List<ApplicationDTO> applicationList = applicationRepository.findAll()
                .stream()
                .map(applicationMapper::entityToDto)
                .collect(Collectors.toList());
        logger.info("All found applications\n{}", applicationList);
        return applicationList;
    }
}
