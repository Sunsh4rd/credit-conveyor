package com.sunshard.conveyor.deal.entity;

import com.sunshard.conveyor.model.ApplicationStatusHistoryDto;
import com.sunshard.conveyor.model.LoanOfferDto;
import com.sunshard.conveyor.model.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "application")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_id", referencedColumnName = "id")
    private Credit credit;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "applied_offer")
    @JdbcTypeCode(SqlTypes.JSON)
    private LoanOfferDto appliedOffer;

    @Column(name = "sign_date")
    private LocalDateTime signDate;

    @Column(name = "ses_code")
    private String sesCode;

    @Column(name = "status_history", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<ApplicationStatusHistoryDto> statusHistory;
}
