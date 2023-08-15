package com.sunshard.deal.entity;

import com.sunshard.deal.model.PaymentScheduleElement;
import com.sunshard.deal.model.enums.CreditStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long creditId;
    private BigDecimal amount;
    private Integer term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private BigDecimal psk;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<PaymentScheduleElement> paymentSchedule;

    private Boolean insuranceEnabled;
    private Boolean salaryClient;

    @Column(columnDefinition = "varchar")
    private CreditStatus creditStatus;
}
