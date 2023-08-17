package com.sunshard.deal.entity;

import com.sunshard.deal.model.ApplicationStatusHistoryDTO;
import com.sunshard.deal.model.LoanOfferDTO;
import com.sunshard.deal.model.enums.ApplicationStatus;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "application")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Schema(description = "Application data entity")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "application_id")
    @Schema(
            type = "integer",
            format = "int64",
            example = "1",
            description = "application id"
    )
    private Long applicationId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    @Schema(description = "related client data")
    private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_id", referencedColumnName = "credit_id")
    @Schema(description = "related credit data")
    private Credit credit;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Schema(
            type = "enum",
            example = "PREAPPROVAL",
            description = "application status"
    )
    private ApplicationStatus status;

    @Column(name = "creation_date")
    @Schema(
            type = "string",
            format = "date-time",
            example = "2023-10-05",
            description = "creation date"
    )
    private LocalDateTime creationDate;

    @Type(type = "jsonb")
    @Column(name = "applied_offer", columnDefinition = "jsonb")
    @Schema(description = "specified offer used in application")
    private LoanOfferDTO appliedOffer;

    @Column(name = "sign_date")
    @Schema(
            type = "string",
            format = "date-time",
            example = "2023-10-05",
            description = "sign date"
    )
    private LocalDateTime signDate;

    @Column(name = "ses_code")
    @Schema(
            type = "string",
            description = "session code"
    )
    private String sesCode;

    @Type(type = "jsonb")
    @Column(name = "status_history", columnDefinition = "jsonb")
    @Schema(description = "application status history")
    private List<ApplicationStatusHistoryDTO> statusHistory;
}
