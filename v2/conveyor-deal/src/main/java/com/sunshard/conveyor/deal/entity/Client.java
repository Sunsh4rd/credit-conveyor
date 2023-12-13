package com.sunshard.conveyor.deal.entity;

import com.sunshard.conveyor.deal.entity.json.Passport;
import com.sunshard.conveyor.model.EmploymentDto;
import com.sunshard.conveyor.model.enums.Gender;
import com.sunshard.conveyor.model.enums.MaritalStatus;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;

import jakarta.persistence.*;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;

@Entity
@Table(name = "client")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "marital_status")
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Column(name = "dependent_amount")
    private Integer dependentAmount;

    @Column(name = "account")
    private String account;

    @Column(name = "passport")
    @JdbcTypeCode(SqlTypes.JSON)
    private Passport passport;

    @Column(name = "employment")
    @JdbcTypeCode(SqlTypes.JSON)
    private EmploymentDto employment;
}
