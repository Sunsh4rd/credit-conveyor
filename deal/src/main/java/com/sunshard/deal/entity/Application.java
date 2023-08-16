package com.sunshard.deal.entity;

import com.sunshard.deal.model.ApplicationStatusHistoryDTO;
import com.sunshard.deal.model.LoanOfferDTO;
import com.sunshard.deal.model.enums.ApplicationStatus;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@TypeDefs({@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)})
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long applicationId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clientId", referencedColumnName = "clientId")
    private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creditId", referencedColumnName = "creditId")
    private Credit credit;

    @Column(columnDefinition = "varchar")
    private ApplicationStatus status;

    private LocalDateTime creationDate;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private LoanOfferDTO appliedOffer;

    private LocalDateTime signDate;
    private String sesCode;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<ApplicationStatusHistoryDTO> statusHistory;
}
