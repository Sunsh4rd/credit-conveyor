package com.sunshard.deal.entity;

import com.sunshard.deal.entity.json.Passport;
import com.sunshard.deal.model.EmploymentDTO;
import com.sunshard.deal.model.enums.Gender;
import com.sunshard.deal.model.enums.MaritalStatus;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "client")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Client data")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "client_id")
    @Schema(
            type = "integer",
            format = "int64",
            example = "1",
            description = "client id"
    )
    private Long clientId;

    @Column(name = "first_name")
    @Schema(
            type = "string",
            example = "Moses",
            description = "your first name"
    )
    private String firstName;

    @Column(name = "last_name")
    @Schema(
            type = "string",
            example = "Jackson",
            description = "your last name"
    )
    private String lastName;

    @Column(name = "middle_name")
    @Schema(
            type = "string",
            example = "Fitzgerald",
            description = "your middle name"
    )
    private String middleName;

    @Column(name = "birth_date")
    @Schema(
            type = "string",
            format = "date",
            example = "2000-08-04",
            description = "your birthdate"
    )
    private LocalDate birthDate;

    @Column(name = "email")
    @Schema(
            type = "string",
            example = "your@mail.com",
            description = "your email"
    )
    private String email;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    @Schema(
            type = "enum",
            example = "MALE",
            description = "loaner's gender"
    )
    private Gender gender;

    @Column(name = "marital_status")
    @Enumerated(EnumType.STRING)
    @Schema(
            type = "enum",
            example = "SINGLE",
            description = "loaner's marital status"
    )
    private MaritalStatus maritalStatus;

    @Column(name = "dependent_amount")
    @Schema(
            type = "integer",
            example = "3",
            description = "loaner's amount of dependents"
    )
    private Integer dependentAmount;

    @Column(name = "account")
    @Schema(
            type = "string",
            example = "98765432100123456789",
            description = "loaner's bank account"
    )
    private String account;

    @Type(type = "jsonb")
    @Column(name = "passport", columnDefinition = "jsonb")
    @Schema(description = "loaner's passport data")
    private Passport passport;

    @Type(type = "jsonb")
    @Column(name = "employment", columnDefinition = "jsonb")
    @Schema(description = "loaner's employment data")
    private EmploymentDTO employment;
}
