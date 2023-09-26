package com.sunshard.deal.mapper;

import com.sunshard.deal.entity.Client;
import com.sunshard.deal.entity.json.Passport;
import com.sunshard.deal.model.EmploymentDTO;
import com.sunshard.deal.model.LoanApplicationRequestDTO;
import com.sunshard.deal.model.enums.Gender;
import com.sunshard.deal.model.enums.MaritalStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientMapperTest {

    @Autowired
    private ClientMapper clientMapper;

//    @Test
//    void dtoToEntity_success() {
//        LoanApplicationRequestDTO request = LoanApplicationRequestDTO.builder()
//                .amount(BigDecimal.valueOf(100000))
//                .birthDate(LocalDate.parse("2000-01-01"))
//                .email("client@mail.com")
//                .firstName("first")
//                .lastName("last")
//                .middleName("middle")
//                .passportNumber("123456")
//                .passportSeries("1234")
//                .term(12)
//                .build();
//        EmploymentDTO employmentDTO = EmploymentDTO.builder().build();
//        Passport passport = Passport.builder()
//                .number("123456")
//                .series("1234")
//                .issueBranch("branch")
//                .issueDate(LocalDate.parse("2020-01-01"))
//                .build();
//        Client client = Client.builder()
//                .clientId(1L)
//                .birthDate(LocalDate.parse("2000-01-01"))
//                .email("client@mail.com")
//                .firstName("first")
//                .lastName("last")
//                .middleName("middle")
//                .passport(passport)
//                .account("101010")
//                .dependentAmount(3)
//                .employment(employmentDTO)
//                .gender(Gender.MALE)
//                .maritalStatus(MaritalStatus.SINGLE)
//                .build();
//        assertEquals(client, clientMapper.dtoToEntity(request));
//    }

    @Test
    void dtoToEntity_failure() {
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
        EmploymentDTO employmentDTO = EmploymentDTO.builder().build();
        Passport passport = Passport.builder()
                .number("123456")
                .series("1234")
                .issueBranch("branch")
                .issueDate(LocalDate.parse("2020-01-01"))
                .build();
        Client client = Client.builder()
                .clientId(1L)
                .birthDate(LocalDate.parse("2000-01-01"))
                .email("client@mail.com")
                .firstName("first")
                .lastName("last")
                .middleName("middle")
                .passport(passport)
                .account("101010")
                .dependentAmount(3)
                .employment(employmentDTO)
                .gender(Gender.MALE)
                .maritalStatus(MaritalStatus.SINGLE)
                .build();
        assertNotEquals(client, clientMapper.dtoToEntity(request));
    }
}