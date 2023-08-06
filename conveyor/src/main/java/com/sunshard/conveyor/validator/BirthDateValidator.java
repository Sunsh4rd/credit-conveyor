package com.sunshard.conveyor.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BirthDateValidator implements ConstraintValidator<BirthDate, LocalDate> {
    @Override
    public void initialize(BirthDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext constraintValidatorContext) {
        return ChronoUnit.YEARS.between(birthDate, LocalDate.now()) > 18;
    }
}
