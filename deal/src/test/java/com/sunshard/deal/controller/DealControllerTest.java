package com.sunshard.deal.controller;

import com.sunshard.deal.model.EmploymentDTO;
import com.sunshard.deal.model.FinishRegistrationRequestDTO;
import com.sunshard.deal.model.LoanApplicationRequestDTO;
import com.sunshard.deal.model.LoanOfferDTO;
import com.sunshard.deal.model.enums.EmploymentStatus;
import com.sunshard.deal.service.impl.DealServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class DealControllerTest {

    @InjectMocks
    DealController dealController;

    @Mock
    DealServiceImpl dealService;

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
        dealController.createLoanOffers(request);
        Mockito.verify(dealService, Mockito.times(1)).createLoanOffers(request);
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
        assertEquals(HttpStatus.OK, dealController.applyLoanOffer(loanOfferDTO).getStatusCode());
    }

    @Test
    void calculateCreditData_responseBodyIsEmpty() {
        Long applicationId = 14L;
        FinishRegistrationRequestDTO finishRegistrationRequest = FinishRegistrationRequestDTO.builder()
                .employment(EmploymentDTO.builder()
                        .employmentStatus(EmploymentStatus.UNEMPLOYED)
                        .build())
                .build();
        assertNull(dealController.calculateCreditData(applicationId, finishRegistrationRequest).getBody());
    }
}