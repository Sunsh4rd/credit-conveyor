package com.sunshard.deal.entity;

import com.sunshard.deal.model.PaymentScheduleElement;
import com.sunshard.deal.model.enums.CreditStatus;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "credit")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Calculated credit data entity")
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "credit_id")
    @Schema(
            type = "integer",
            format = "int64",
            example = "1",
            description = "credit id"
    )
    private Long creditId;

    @Column(name = "amount")
    @Schema(type = "number",
            example = "300000",
            description = "amount of your loan"
    )
    private BigDecimal amount;

    @Column(name = "term")
    @Schema(type = "integer",
            example = "18",
            description = "term of your loan"
    )
    private Integer term;

    @Column(name = "monthly_payment")
    @Schema(type = "number",
            example = "18047.55",
            description = "monthly payment amount"
    )
    private BigDecimal monthlyPayment;

    @Column(name = "rate")
    @Schema(type = "number",
            example = "12.50",
            description = "loan rate"
    )
    private BigDecimal rate;

    @Column(name = "psk")
    @Schema(type = "number",
            example = "448000",
            description = "full loan price"
    )
    private BigDecimal psk;

    @Type(type = "jsonb")
    @Column(name = "payment_schedule", columnDefinition = "jsonb")
    @Schema(
            description = "list-organized payment schedule data"
    )
    private List<PaymentScheduleElement> paymentSchedule;

    @Column(name = "insurance_enabled")
    @Schema(type = "boolean",
            example = "true",
            description = "shows whether insurance is enabled"
    )
    private Boolean insuranceEnabled;

    @Column(name = "salary_client")
    @Schema(type = "boolean",
            example = "true",
            description = "shows whether loaner is a salary client"
    )
    private Boolean salaryClient;

    @Column(name = "credit_status")
    @Enumerated(EnumType.STRING)
    @Schema(
            type = "enum",
            example = "CALCULATED",
            description = "current credit status"
    )
    private CreditStatus creditStatus;
}
