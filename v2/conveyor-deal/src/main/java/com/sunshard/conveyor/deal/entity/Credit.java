package com.sunshard.conveyor.deal.entity;

import com.sunshard.conveyor.model.PaymentScheduleElement;
import com.sunshard.conveyor.model.enums.CreditStatus;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;

import jakarta.persistence.*;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "credit")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "term")
    private Integer term;

    @Column(name = "monthly_payment")
    private BigDecimal monthlyPayment;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "psk")
    private BigDecimal psk;

    @Column(name = "payment_schedule")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<PaymentScheduleElement> paymentSchedule;

    @Column(name = "insurance_enabled")
    private Boolean insuranceEnabled;

    @Column(name = "salary_client")
    private Boolean salaryClient;

    @Column(name = "credit_status")
    @Enumerated(EnumType.STRING)
    private CreditStatus creditStatus;
}
