package com.sunshard.conveyor.core.service;

import com.sunshard.conveyor.model.CreditDto;
import com.sunshard.conveyor.model.ScoringDataDto;

public interface CalculationService {

    CreditDto calculateCreditData(ScoringDataDto scoringData);

}
