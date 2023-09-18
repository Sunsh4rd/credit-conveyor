package com.sunshard.deal.model;

import com.sunshard.deal.entity.json.Passport;
import com.sunshard.deal.model.enums.Gender;
import com.sunshard.deal.model.enums.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class ClientDTO {
    private Long clientId;
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate birthDate;
    private String email;
    private Gender gender;
    private MaritalStatus maritalStatus;
    private Integer dependentAmount;
    private String account;
    private Passport passport;
    private EmploymentDTO employment;
}
