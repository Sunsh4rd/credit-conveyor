package com.sunshard.conveyor.config.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Getter
@Configuration
public class CreditProperties {
    @Value("${credit.basic-rate}")
    private BigDecimal basicRate;

    @Value("${credit.insurance-price}")
    private BigDecimal insurancePrice;
}
