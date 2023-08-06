package com.sunshard.conveyor.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Constants {
    public static final BigDecimal TWO = BigDecimal.valueOf(2);
    public static final BigDecimal THREE = BigDecimal.valueOf(3);
    public static final BigDecimal FOUR = BigDecimal.valueOf(4);
    public static final BigDecimal TWELVE = BigDecimal.valueOf(12);
    public static final BigDecimal TWENTY = BigDecimal.valueOf(20);
    public static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
    public static final MathContext SCALE = new MathContext(7, RoundingMode.HALF_UP);
}
