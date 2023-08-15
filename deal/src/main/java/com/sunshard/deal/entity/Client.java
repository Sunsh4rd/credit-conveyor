package com.sunshard.deal.entity;

import com.sunshard.deal.entity.json.Employment;
import com.sunshard.deal.entity.json.Passport;
import com.sunshard.deal.model.enums.Gender;
import com.sunshard.deal.model.enums.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long clientId;
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate birthDate;
    private String email;

    @Column(columnDefinition = "varchar")
    private Gender gender;

    @Column(columnDefinition = "varchar")
    private MaritalStatus maritalStatus;

    private Integer dependentAmount;
    private String account;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Passport passport;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Employment employment;
}
