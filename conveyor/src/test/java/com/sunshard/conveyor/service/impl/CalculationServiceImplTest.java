package com.sunshard.conveyor.service.impl;

import com.sunshard.conveyor.model.EmploymentDTO;
import com.sunshard.conveyor.model.ScoringDataDTO;
import com.sunshard.conveyor.model.enums.EmploymentStatus;
import com.sunshard.conveyor.model.enums.Gender;
import com.sunshard.conveyor.model.enums.MaritalStatus;
import com.sunshard.conveyor.model.enums.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class CalculationServiceImplTest {

    @InjectMocks
    CalculationServiceImpl calculationService;

    @Mock
    ScoringServiceImpl scoringService;

    @Test
    void calculateCreditData_verifyTimesValidateScoringDataInvoked() {
        EmploymentDTO employmentData = EmploymentDTO.builder()
                .employmentStatus(EmploymentStatus.EMPLOYED)
                .employerINN("012345678912")
                .salary(BigDecimal.valueOf(60000))
                .position(Position.WORKER)
                .workExperienceCurrent(8)
                .workExperienceTotal(16)
                .build();
        ScoringDataDTO scoringData = ScoringDataDTO.builder()
                .amount(BigDecimal.valueOf(300000))
                .term(18)
                .firstName("first")
                .lastName("last")
                .middleName("middle")
                .gender(Gender.MALE)
                .birthDate(LocalDate.parse("2000-01-01"))
                .passportNumber("123456")
                .passportSeries("1234")
                .passportIssueDate(LocalDate.parse("2020-01-01"))
                .passportIssueBranch("Issue branch")
                .maritalStatus(MaritalStatus.SINGLE)
                .dependentAmount(0)
                .employment(employmentData)
                .account("10102020")
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();
        calculationService.calculateCreditData(scoringData);
        Mockito.verify(scoringService, Mockito.times(1)).validateScoringData(employmentData, scoringData.getAmount(), scoringData.getBirthDate());
    }

}