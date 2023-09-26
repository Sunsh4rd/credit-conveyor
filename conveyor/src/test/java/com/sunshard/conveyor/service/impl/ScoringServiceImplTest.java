package com.sunshard.conveyor.service.impl;

import com.sunshard.conveyor.exception.CreditDeniedException;
import com.sunshard.conveyor.model.EmploymentDTO;
import com.sunshard.conveyor.model.PaymentScheduleElement;
import com.sunshard.conveyor.model.ScoringDataDTO;
import com.sunshard.conveyor.model.enums.EmploymentStatus;
import com.sunshard.conveyor.model.enums.Gender;
import com.sunshard.conveyor.model.enums.MaritalStatus;
import com.sunshard.conveyor.model.enums.Position;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ScoringServiceImplTest {

    @Autowired
    ScoringServiceImpl scoringService;

    @Test
    void calculateRate_insuranceEnabledAndIsSalaryClient_Equals11() {
        assertEquals(BigDecimal.valueOf(11), scoringService.calculateRate(true, true));
    }

    @Test
    void calculateRate_insuranceEnabledAndIsNotSalaryClient_Equals12() {
        assertEquals(BigDecimal.valueOf(12), scoringService.calculateRate(true, false));
    }

    @Test
    void calculateRate_insuranceDisabledAndIsSalaryClient_Equals14() {
        assertEquals(BigDecimal.valueOf(14), scoringService.calculateRate(false, true));
    }

    @Test
    void calculateRate_insuranceDisabledAndIsNotSalaryClient_Equals15() {
        assertEquals(BigDecimal.valueOf(15), scoringService.calculateRate(false, false));
    }

    @Test
    void calculateMonthlyRate_RateEquals15() {
        assertEquals(BigDecimal.valueOf(0.0125), scoringService.calculateMonthlyRate(BigDecimal.valueOf(15)));
    }

    @Test
    void calculateMonthlyRate_RateEquals11() {
        assertEquals(BigDecimal.valueOf(0.009166667), scoringService.calculateMonthlyRate(BigDecimal.valueOf(11)));
    }

    @Test
    void calculateMonthlyRate_RateEquals12() {
        assertEquals(BigDecimal.valueOf(0.01), scoringService.calculateMonthlyRate(BigDecimal.valueOf(12)));
    }

    @Test
    void calculateMonthlyRate_RateEquals14() {
        assertEquals(BigDecimal.valueOf(0.01166667), scoringService.calculateMonthlyRate(BigDecimal.valueOf(14)));
    }

    @Test
    void calculateLoanAmountBasedOnInsuranceStatus_insuranceEnabled() {
        assertEquals(BigDecimal.valueOf(300000),
                scoringService.calculateLoanAmountBasedOnInsuranceStatus(
                        BigDecimal.valueOf(200000), true
                )
        );
    }

    @Test
    void calculateLoanAmountBasedOnInsuranceStatus_insuranceDisabled() {
        assertEquals(BigDecimal.valueOf(200000),
                scoringService.calculateLoanAmountBasedOnInsuranceStatus(
                        BigDecimal.valueOf(200000), false
                )
        );
    }

    @Test
    void calculateMonthlyPayment_1() {
        assertEquals(0, BigDecimal.valueOf(18715.437000).compareTo(
                scoringService.calculateMonthlyPayment(
                        BigDecimal.valueOf(0.0125000),
                        BigDecimal.valueOf(300000),
                        18
                )
                )
        );
    }

    @Test
    void calculateMonthlyPayment_2() {
        assertEquals(0, BigDecimal.valueOf(20804.016000).compareTo(
                scoringService.calculateMonthlyPayment(
                        BigDecimal.valueOf(0.0125000),
                        BigDecimal.valueOf(300000),
                        16
                )
                )
        );
    }

    @Test
    void calculateMonthlyPayment_3() {
        assertEquals(0, BigDecimal.valueOf(23310.1530000).compareTo(
                        scoringService.calculateMonthlyPayment(
                                BigDecimal.valueOf(0.01166667),
                                BigDecimal.valueOf(450000),
                                22
                        )
                )
        );
    }

    @Test
    void calculateTotalAmount_1() {
        assertEquals(BigDecimal.valueOf(336877.92), scoringService.calculateTotalAmount(
                BigDecimal.valueOf(18715.440),
                18
        ));
    }

    @Test
    void calculateTotalAmount_2() {
        assertEquals(BigDecimal.valueOf(512823.366), scoringService.calculateTotalAmount(
                BigDecimal.valueOf(23310.1530000),
                22
        ));
    }

    @Test
    void calculateTotalAmount_3() {
        assertEquals(BigDecimal.valueOf(332864.256), scoringService.calculateTotalAmount(
                BigDecimal.valueOf(20804.016000),
                16
        ));
    }

    @Test
    void validateScoringData_throwsCreditDeniedException_amountIsTooLarge() {
        assertThrows(CreditDeniedException.class,
                () -> scoringService.validateScoringData(
                        EmploymentDTO.builder()
                                .employmentStatus(EmploymentStatus.EMPLOYED)
                                .employerINN("012345678912")
                                .salary(BigDecimal.valueOf(10000))
                                .position(Position.WORKER)
                                .workExperienceCurrent(8)
                                .workExperienceTotal(16)
                                .build(),
                        BigDecimal.valueOf(300000),
                        LocalDate.parse("2000-01-01")
                )
        );
    }

    @Test
    void validateScoringData_throwsCreditDeniedException_loanerUnemployed() {
        assertThrows(CreditDeniedException.class,
                () -> scoringService.validateScoringData(
                        EmploymentDTO.builder()
                                .employmentStatus(EmploymentStatus.UNEMPLOYED)
                                .employerINN("012345678912")
                                .salary(BigDecimal.ZERO)
                                .position(Position.WORKER)
                                .workExperienceCurrent(8)
                                .workExperienceTotal(16)
                                .build(),
                        BigDecimal.valueOf(300000),
                        LocalDate.parse("2000-01-01")
                )
        );
    }

    @Test
    void validateScoringData_throwsCreditDeniedException_loanerTooYoung() {
        assertThrows(CreditDeniedException.class,
                () -> scoringService.validateScoringData(
                        EmploymentDTO.builder()
                                .employmentStatus(EmploymentStatus.EMPLOYED)
                                .employerINN("012345678912")
                                .salary(BigDecimal.valueOf(60000))
                                .position(Position.WORKER)
                                .workExperienceCurrent(8)
                                .workExperienceTotal(16)
                                .build(),
                        BigDecimal.valueOf(300000),
                        LocalDate.parse("2004-01-01")
                )
        );
    }

    @Test
    void validateScoringData_throwsCreditDeniedException_loanerTooOld() {
        assertThrows(CreditDeniedException.class,
                () -> scoringService.validateScoringData(
                        EmploymentDTO.builder()
                                .employmentStatus(EmploymentStatus.EMPLOYED)
                                .employerINN("012345678912")
                                .salary(BigDecimal.valueOf(150000))
                                .position(Position.WORKER)
                                .workExperienceCurrent(8)
                                .workExperienceTotal(16)
                                .build(),
                        BigDecimal.valueOf(300000),
                        LocalDate.parse("1962-01-01")
                )
        );
    }

    @Test
    void validateScoringData_throwsCreditDeniedException_tooLittleExperience() {
        assertThrows(CreditDeniedException.class,
                () -> scoringService.validateScoringData(
                        EmploymentDTO.builder()
                                .employmentStatus(EmploymentStatus.EMPLOYED)
                                .employerINN("012345678912")
                                .salary(BigDecimal.valueOf(60000))
                                .position(Position.WORKER)
                                .workExperienceCurrent(8)
                                .workExperienceTotal(11)
                                .build(),
                        BigDecimal.valueOf(300000),
                        LocalDate.parse("2000-01-01")
                )
        );
    }

    @Test
    void calculateCreditRate_Equals11() {
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
        assertEquals(BigDecimal.valueOf(11), scoringService.calculateCreditRate(scoringData));
    }

    @Test
    void calculateCreditRate_Equals9() {
        EmploymentDTO employmentData = EmploymentDTO.builder()
                .employmentStatus(EmploymentStatus.BUSINESS_OWNER)
                .employerINN("012345678912")
                .salary(BigDecimal.valueOf(60000))
                .position(Position.OWNER)
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
                .maritalStatus(MaritalStatus.MARRIED)
                .dependentAmount(0)
                .employment(employmentData)
                .account("10102020")
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();
        assertEquals(BigDecimal.valueOf(9), scoringService.calculateCreditRate(scoringData));
    }

    @Test
    void calculateCreditRate_Equals17() {
        EmploymentDTO employmentData = EmploymentDTO.builder()
                .employmentStatus(EmploymentStatus.EMPLOYED)
                .employerINN("012345678912")
                .salary(BigDecimal.valueOf(60000))
                .position(Position.TOP_MANAGER)
                .workExperienceCurrent(8)
                .workExperienceTotal(16)
                .build();
        ScoringDataDTO scoringData = ScoringDataDTO.builder()
                .amount(BigDecimal.valueOf(300000))
                .term(18)
                .firstName("first")
                .lastName("last")
                .middleName("middle")
                .gender(Gender.NON_BINARY)
                .birthDate(LocalDate.parse("2000-01-01"))
                .passportNumber("123456")
                .passportSeries("1234")
                .passportIssueDate(LocalDate.parse("2020-01-01"))
                .passportIssueBranch("Issue branch")
                .maritalStatus(MaritalStatus.DIVORCED)
                .dependentAmount(2)
                .employment(employmentData)
                .account("10102020")
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();
        assertEquals(BigDecimal.valueOf(17), scoringService.calculateCreditRate(scoringData));
    }

    @Test
    void calculatePaymentSchedule_correctAmountOfElements() {
        List<PaymentScheduleElement> paymentSchedule = scoringService.calculatePaymentSchedule(
                BigDecimal.valueOf(300000),
                18,
                BigDecimal.valueOf(15),
                BigDecimal.valueOf(18715.44)
        );
        assertEquals(18, paymentSchedule.size());
    }

//    @Test
//    @Disabled
//    void calculatePaymentSchedule_correctRemainingDebt() {
//        List<PaymentScheduleElement> paymentSchedule = scoringService.calculatePaymentSchedule(
//                BigDecimal.valueOf(450000),
//                24,
//                BigDecimal.valueOf(10),
//                BigDecimal.valueOf(20765.2185)
//        );
//        assertEquals(0, BigDecimal.valueOf(381117.1330).compareTo(paymentSchedule.get(3).getRemainingDebt()));
//    }

    @Test
    void calculatePsk_1() {
        List<PaymentScheduleElement> paymentSchedule = scoringService.calculatePaymentSchedule(
                BigDecimal.valueOf(450000),
                24,
                BigDecimal.valueOf(10),
                BigDecimal.valueOf(20765.2185)
        );
        assertEquals(0, BigDecimal.valueOf(10.7478).compareTo(
                scoringService.calculatePsk(paymentSchedule, BigDecimal.valueOf(450000))));
    }

    @Test
    void calculatePsk_2() {
        List<PaymentScheduleElement> paymentSchedule = scoringService.calculatePaymentSchedule(
                BigDecimal.valueOf(1500000),
                36,
                BigDecimal.valueOf(6),
                BigDecimal.valueOf(45632.91)
        );
        assertEquals(0, BigDecimal.valueOf(9.519).compareTo(
                scoringService.calculatePsk(paymentSchedule, BigDecimal.valueOf(1500000))));
    }
}