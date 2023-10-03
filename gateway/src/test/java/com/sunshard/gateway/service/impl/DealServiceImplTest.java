package com.sunshard.gateway.service.impl;

import com.sunshard.gateway.client.DealFeignClient;
import com.sunshard.gateway.model.FinishRegistrationRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DealServiceImplTest {

    @InjectMocks
    DealServiceImpl dealService;

    @Mock
    DealFeignClient dealFeignClient;

    @Test
    void calculateCreditData_success() {
        FinishRegistrationRequestDTO finishRegistrationRequest = FinishRegistrationRequestDTO.builder().build();
        dealService.calculateCreditData(160L, finishRegistrationRequest);
        Mockito.verify(dealFeignClient, Mockito.times(1))
                .calculateCreditData(160L, finishRegistrationRequest);
    }

    @Test
    void sendDocuments_success() {
        dealService.sendDocuments(160L);
        Mockito.verify(dealFeignClient, Mockito.times(1)).sendDocuments(160L);
    }

    @Test
    void signRequest_success() {
        dealService.signRequest(160L);
        Mockito.verify(dealFeignClient, Mockito.times(1)).signRequest(160L);
    }

    @Test
    void signDocuments_success() {
        dealService.signDocuments(160L, 1234);
        Mockito.verify(dealFeignClient, Mockito.times(1))
                .signDocuments(160L, 1234);
    }
}