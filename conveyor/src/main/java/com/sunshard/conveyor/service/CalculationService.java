package com.sunshard.conveyor.service;

import com.sunshard.conveyor.model.CreditDTO;
import com.sunshard.conveyor.model.ScoringDataDTO;

public interface CalculationService {
    CreditDTO calculation(ScoringDataDTO scoringData);
}
