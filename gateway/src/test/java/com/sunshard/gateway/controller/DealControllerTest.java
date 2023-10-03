package com.sunshard.gateway.controller;

import com.sunshard.gateway.model.FinishRegistrationRequestDTO;
import com.sunshard.gateway.model.LoanOfferDTO;
import com.sunshard.gateway.service.impl.DealServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DealControllerTest {

    @InjectMocks
    DealController dealController;

    @Mock
    DealServiceImpl dealService;

    @Test
    void calculateCreditData_success_success() {
        FinishRegistrationRequestDTO finishRegistrationRequest = FinishRegistrationRequestDTO.builder().build();
        dealController.calculateCreditData(160L, finishRegistrationRequest);
        Mockito.verify(dealService, Mockito.times(1))
                .calculateCreditData(160L, finishRegistrationRequest);
    }

    @Test
    void sendDocuments_success() {
        dealController.sendDocuments(160L);
        Mockito.verify(dealService, Mockito.times(1))
                .sendDocuments(160L);
    }

    @Test
    void signRequest_success() {
        dealController.signRequest(160L);
        Mockito.verify(dealService, Mockito.times(1))
                .signRequest(160L);
    }

    @Test
    void signDocuments_success() {
        dealController.signDocuments(160L, 1234);
        Mockito.verify(dealService, Mockito.times(1))
                .signDocuments(160L, 1234);
    }
}