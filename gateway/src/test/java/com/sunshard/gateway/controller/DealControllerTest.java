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
    void calculateCreditData_success() {
        FinishRegistrationRequestDTO finishRegistrationRequest = FinishRegistrationRequestDTO.builder().build();
        dealController.calculateCreditData(160L, finishRegistrationRequest);
        Mockito.verify(dealService, Mockito.times(1))
                .calculateCreditData(160L, finishRegistrationRequest);
    }

    @Test
    void sendDocuments() {
    }

    @Test
    void signRequest() {
    }

    @Test
    void signDocuments() {
    }
}