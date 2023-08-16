package com.sunshard.deal.entity;

import com.sunshard.deal.model.PaymentScheduleElement;
import com.sunshard.deal.model.enums.CreditStatus;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
