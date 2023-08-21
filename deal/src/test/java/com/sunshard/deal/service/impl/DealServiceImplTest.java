package com.sunshard.deal.service.impl;

import com.sunshard.deal.client.CreditConveyorFeignClient;
import com.sunshard.deal.exception.ApplicationNotFoundException;
import com.sunshard.deal.exception.LoanOffersNotCreatedException;
import com.sunshard.deal.mapper.ApplicationMapper;
import com.sunshard.deal.mapper.ClientMapper;
import com.sunshard.deal.mapper.CreditMapper;
import com.sunshard.deal.mapper.ScoringDataMapper;
import com.sunshard.deal.model.EmploymentDTO;
import com.sunshard.deal.model.FinishRegistrationRequestDTO;
import com.sunshard.deal.model.LoanApplicationRequestDTO;
import com.sunshard.deal.model.LoanOfferDTO;
import com.sunshard.deal.model.enums.EmploymentStatus;
import com.sunshard.deal.repository.ApplicationRepository;
import com.sunshard.deal.repository.ClientRepository;
import com.sunshard.deal.repository.CreditRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class DealServiceImplTest {

    @InjectMocks
    DealServiceImpl dealService;

    @Mock
    ClientRepository clientRepository;

    @Mock
    ApplicationRepository applicationRepository;

    @Mock
    CreditRepository creditRepository;

    @Mock
    ClientMapper clientMapper;

    @Mock
    CreditMapper creditMapper;

    @Mock
    ApplicationMapper applicationMapper;

    @Mock
    ScoringDataMapper scoringDataMapper;

    @Mock
    CreditConveyorFeignClient creditConveyorFeignClient;

    @Test
    void createLoanOffers_throwsLoanOffersNotCreatedException() {
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
        Mockito.doReturn(null).when(creditConveyorFeignClient).createLoanOffers(request);
        assertThrows(LoanOffersNotCreatedException.class, () -> dealService.createLoanOffers(request));
    }

    @Test
    void applyLoanOffer_throwsApplicationNotFoundException() {
        LoanOfferDTO loanOffer = LoanOfferDTO.builder()
                .applicationId(14L)
                .requestedAmount(BigDecimal.valueOf(300000))
                .totalAmount(BigDecimal.valueOf(439070.76))
                .term(18)
                .monthlyPayment(BigDecimal.valueOf(24392.82))
                .rate(BigDecimal.valueOf(12))
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build();

        assertThrows(ApplicationNotFoundException.class, () -> dealService.applyLoanOffer(loanOffer));
    }

    @Test
    void calculateCreditData_test() {
        Long applicationId = 14L;
        FinishRegistrationRequestDTO finishRegistrationRequest = FinishRegistrationRequestDTO.builder()
                .employment(EmploymentDTO.builder()
                        .employmentStatus(EmploymentStatus.SELF_EMPLOYED)
                        .build())
                .build();
        Mockito.verify(scoringDataMapper,Mockito.times(1))
                .from(applicationRepository.findById(applicationId).get(), finishRegistrationRequest);
    }
}