package com.sunshard.deal.service.impl;

import com.sunshard.deal.exception.ApplicationNotFoundException;
import com.sunshard.deal.mapper.ApplicationMapper;
import com.sunshard.deal.mapper.ClientMapper;
import com.sunshard.deal.mapper.CreditMapper;
import com.sunshard.deal.model.ApplicationDTO;
import com.sunshard.deal.model.ClientDTO;
import com.sunshard.deal.model.CreditDTO;
import com.sunshard.deal.repository.ApplicationRepository;
import com.sunshard.deal.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final ClientMapper clientMapper;
    private final CreditMapper creditMapper;

    private static final Logger logger = LogManager.getLogger(ClientServiceImpl.class.getName());

    @Override
    public String getCreditData(Long applicationId) {
        ApplicationDTO application = applicationMapper.entityToDto(applicationRepository.findById(applicationId).get());
        ClientDTO client = clientMapper.entityToDto(application.getClient());
        CreditDTO credit = creditMapper.entityToDto(application.getCredit());
        String creditData = String.join("\n", application.toString(), credit.toString(), client.toString());
        logger.info("Credit data for documents for application {}", applicationId);
        return creditData;
    }

    @Override
    public String getSesCode(Long applicationId) {
        logger.info("Getting ses code for application {}", applicationId);
        return applicationRepository.findById(applicationId).orElseThrow(
                () -> new ApplicationNotFoundException(applicationId)
        ).getSesCode();
    }
}
