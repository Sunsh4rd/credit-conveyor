package com.sunshard.conveyor.service.impl;

import com.sunshard.conveyor.model.LoanApplicationRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class OfferServiceImplTest {
    @InjectMocks
    private OfferServiceImpl offerService;

    @Mock
    private ScoringServiceImpl scoringService;

    @Test
    void createLoanOffers_verifyTimesCalculateRateInvoked() {
        LoanApplicationRequestDTO request = LoanApplicationRequestDTO.builder()
                .amount(BigDecimal.valueOf(300000))
                .term(18)
                .firstName("first")
                .lastName("last")
                .middleName("middle")
                .email("your@mail.com")
                .birthDate(LocalDate.parse("2000-01-01"))
                .passportSeries("1231")
                .passportNumber("123456")
                .build();

        offerService.createLoanOffers(request);

        Mockito.verify(scoringService, Mockito.times(1)).calculateRate(true, true);
        Mockito.verify(scoringService, Mockito.times(1)).calculateRate(true, false);
        Mockito.verify(scoringService, Mockito.times(1)).calculateRate(false, true);
        Mockito.verify(scoringService, Mockito.times(1)).calculateRate(false, false);
    }
}