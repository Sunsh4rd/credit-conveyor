package com.sunshard.conveyor.service.impl;

import com.sunshard.conveyor.model.LoanApplicationRequestDTO;
import com.sunshard.conveyor.model.LoanOfferDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class OfferServiceImplTest {
    @InjectMocks
    private OfferServiceImpl offerService;

    @Mock
    private ScoringServiceImpl scoringService;

    @Test
    void createLoanOffers() {
        List<LoanOfferDTO> loanOffers = offerService.createLoanOffers(
                LoanApplicationRequestDTO.builder()
                .amount(BigDecimal.valueOf(300000))
                .term(18)
                .firstName("first")
                .lastName("last")
                .middleName("middle")
                .email("your@mail.com")
                .birthdate(LocalDate.parse("2000-01-01"))
                .passportSeries("1231")
                .passportNumber("123456")
                .build()
        );
        assertEquals(4, loanOffers.size());
    }
}