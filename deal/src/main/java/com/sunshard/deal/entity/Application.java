package com.sunshard.deal.entity;

import com.sunshard.deal.model.ApplicationStatusHistoryDTO;
import com.sunshard.deal.model.LoanOfferDTO;
import com.sunshard.deal.model.enums.ApplicationStatus;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "application_id")
    private Long applicationId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_id", referencedColumnName = "credit_id")
    private Credit credit;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Type(type = "jsonb")
    @Column(name = "applied_offer", columnDefinition = "jsonb")
    private LoanOfferDTO appliedOffer;

    @Column(name = "sign_date")
    private LocalDateTime signDate;

    @Column(name = "ses_code")
    private String sesCode;

    @Type(type = "jsonb")
    @Column(name = "status_history", columnDefinition = "jsonb")
    private List<ApplicationStatusHistoryDTO> statusHistory;
}
