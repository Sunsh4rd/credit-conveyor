package com.sunshard.deal.entity.json;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
public class Passport implements Serializable {
    private String series;
    private String number;
    private String issueBranch;
    private LocalDate issueDate;
}
