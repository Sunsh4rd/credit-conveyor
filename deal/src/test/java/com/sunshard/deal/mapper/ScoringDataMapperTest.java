package com.sunshard.deal.mapper;

import com.sunshard.deal.entity.Application;
import com.sunshard.deal.entity.Client;
import com.sunshard.deal.entity.Credit;
import com.sunshard.deal.model.*;
import com.sunshard.deal.model.enums.ApplicationStatus;
import com.sunshard.deal.model.enums.Gender;
import com.sunshard.deal.model.enums.MaritalStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class ScoringDataMapperTest {

    @Autowired
    ScoringDataMapper scoringDataMapper;

    @Test
    void from_success() {
        LocalDateTime timeStamp = LocalDateTime.now();
        Credit credit = Credit.builder().build();
        Client client = Client.builder().build();
        LoanOfferDTO loanOfferDTO = LoanOfferDTO.builder().build();
        EmploymentDTO employment = EmploymentDTO.builder().build();
        List<ApplicationStatusHistoryDTO> applicationStatusHistory = List.of(
                ApplicationStatusHistoryDTO.builder().build()
        );
        FinishRegistrationRequestDTO finishRegistrationRequest = FinishRegistrationRequestDTO.builder()
                .employment(employment)
                .account("111000")
                .dependentAmount(2)
                .gender(Gender.MALE)
                .maritalStatus(MaritalStatus.SINGLE)
                .passportIssueBranch("branch")
                .passportIssueDate(LocalDate.parse("2020-01-01"))
                .build();
        Application application = Application.builder()
                .applicationId(1L)
                .client(client)
                .credit(credit)
                .statusHistory(applicationStatusHistory)
                .signDate(timeStamp)
                .creationDate(timeStamp)
                .status(ApplicationStatus.APPROVED)
                .sesCode("00001111")
                .appliedOffer(loanOfferDTO)
                .build();

        ScoringDataDTO scoringData = ScoringDataDTO.builder()
                .account("111000")
                .dependentAmount(2)
                .employment(employment)
                .gender(Gender.MALE)
                .maritalStatus(MaritalStatus.SINGLE)
                .passportIssueBranch("branch")
                .passportIssueDate(LocalDate.parse("2020-01-01"))
                .build();

        assertEquals(scoringData, scoringDataMapper.from(application, finishRegistrationRequest));
    }

    @Test
    void from_failure() {
        Credit credit = Credit.builder().build();
        Client client = Client.builder().build();
        LoanOfferDTO loanOfferDTO = LoanOfferDTO.builder().build();
        EmploymentDTO employment = EmploymentDTO.builder().build();
        List<ApplicationStatusHistoryDTO> applicationStatusHistory = List.of(
                ApplicationStatusHistoryDTO.builder().build()
        );
        FinishRegistrationRequestDTO finishRegistrationRequest = FinishRegistrationRequestDTO.builder()
                .employment(employment)
                .account("111000")
                .dependentAmount(2)
                .gender(Gender.MALE)
                .maritalStatus(MaritalStatus.SINGLE)
                .passportIssueBranch("branch")
                .passportIssueDate(LocalDate.parse("2020-01-01"))
                .build();
        Application application = Application.builder()
                .applicationId(1L)
                .client(client)
                .credit(credit)
                .statusHistory(applicationStatusHistory)
                .signDate(LocalDateTime.now())
                .creationDate(LocalDateTime.now())
                .status(ApplicationStatus.APPROVED)
                .sesCode("00001111")
                .appliedOffer(loanOfferDTO)
                .build();

        ScoringDataDTO scoringData = ScoringDataDTO.builder()
                .account("111000")
                .amount(BigDecimal.valueOf(100000))
                .dependentAmount(2)
                .employment(employment)
                .gender(Gender.MALE)
                .maritalStatus(MaritalStatus.SINGLE)
                .passportIssueBranch("branch")
                .passportIssueDate(LocalDate.parse("2020-01-01"))
                .build();

        assertNotEquals(scoringData, scoringDataMapper.from(application, finishRegistrationRequest));
    }
}