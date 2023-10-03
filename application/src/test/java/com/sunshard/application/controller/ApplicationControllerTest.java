package com.sunshard.application.controller;

import com.sunshard.application.model.LoanApplicationRequestDTO;
import com.sunshard.application.model.LoanOfferDTO;
import com.sunshard.application.service.impl.ApplicationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ApplicationControllerTest {

    @InjectMocks
    ApplicationController applicationController;

    @Mock
    ApplicationServiceImpl applicationService;

    @Test
    void createLoanOffers_verifyTimesDealServiceInvoked() {
        LoanApplicationRequestDTO request = LoanApplicationRequestDTO.builder()
                .amount(BigDecimal.valueOf(100000))
                .birthDate(LocalDate.parse("2000-01-01"))
                .email("client@mail.com")
                .firstName("first")
                .lastName("last")
                .middleName("middle")
                .passportNumber("098131")
                .passportSeries("1234")
                .term(12)
                .build();
        applicationService.createLoanOffers(request);
        Mockito.verify(applicationService, Mockito.times(1)).createLoanOffers(request);
    }

    @Test
    void applyLoanOffer_success() {
        LoanOfferDTO loanOfferDTO = LoanOfferDTO.builder()
                .applicationId(1L)
                .rate(BigDecimal.valueOf(10))
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .monthlyPayment(BigDecimal.valueOf(10000))
                .requestedAmount(BigDecimal.valueOf(300000))
                .totalAmount(BigDecimal.valueOf(400000))
                .build();
        assertEquals(HttpStatus.OK, applicationController.applyLoanOffer(loanOfferDTO).getStatusCode());
    }
}