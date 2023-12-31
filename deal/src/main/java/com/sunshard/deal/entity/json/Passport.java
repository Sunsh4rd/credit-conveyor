package com.sunshard.deal.entity.json;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Passport {

    private String series;
    private String number;
    private String issueBranch;
    private LocalDate issueDate;
}
