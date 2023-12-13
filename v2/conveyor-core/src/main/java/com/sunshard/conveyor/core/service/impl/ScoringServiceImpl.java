package com.sunshard.conveyor.core.service.impl;

import com.sunshard.conveyor.core.exception.CreditDeniedException;
import com.sunshard.conveyor.core.service.ScoringService;
import com.sunshard.conveyor.model.EmploymentDto;
import com.sunshard.conveyor.model.PaymentScheduleElement;
import com.sunshard.conveyor.model.ScoringDataDto;
import com.sunshard.conveyor.model.enums.EmploymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for encapsulating <i>loan offer</i> and <i>credit data</i> parameters calculations
 */
@Service
@RequiredArgsConstructor
@Configuration
public class ScoringServiceImpl implements ScoringService {
    @Value("${credit.basic-rate}")
    private BigDecimal basicRate;
    @Value("${credit.insurance-price}")
    private BigDecimal insurancePrice;
    private static final BigDecimal TWO_PERCENT = BigDecimal.valueOf(2);
    private static final BigDecimal THREE_PERCENT = BigDecimal.valueOf(3);
    private static final BigDecimal FOUR_PERCENT = BigDecimal.valueOf(4);
    private static final BigDecimal MONTHS_IN_YEAR = BigDecimal.valueOf(12);
    private static final BigDecimal TWENTY_TIMES = BigDecimal.valueOf(20);
    private static final BigDecimal HUNDRED_PERCENT = BigDecimal.valueOf(100);
    private static final MathContext PRECISION = new MathContext(7, RoundingMode.HALF_UP);

    /**
     * Calculate <i>loan rate</i> based on <i>isInsuranceEnabled</i> and <i>isSalaryClient</i>.<br>
     * If <i>isInsuranceEnabled</i> is <i>true</i> rate is decreased by 3.<br>
     * If <i>isSalaryClient</i> is <i>true</i> rate is decreased by 1.<br>
     *
     * @param isInsuranceEnabled specifies if insurance is enabled for <i>loan offer</i>
     * @param isSalaryClient     specifies if loaner is a salary client
     * @return Calculated <i>loan rate</i>
     */
    @Override
    public BigDecimal calculateRate(Boolean isInsuranceEnabled, Boolean isSalaryClient) {
        BigDecimal currentRate = basicRate;
        BigDecimal rate = isInsuranceEnabled ? currentRate.subtract(THREE_PERCENT) : currentRate;
        return isSalaryClient
                ? rate.subtract(BigDecimal.ONE).setScale(2, RoundingMode.HALF_UP)
                : rate.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calculate <i>monthly rate</i> based on <i>loan rate</i> by formula:<br>
     * rate / (12 * 100)
     *
     * @param rate received <i>loan rate</i>
     * @return calculated <i>monthly rate</i>
     */
    @Override
    public BigDecimal calculateMonthlyRate(BigDecimal rate) {
        return rate.divide(HUNDRED_PERCENT.multiply(MONTHS_IN_YEAR), PRECISION).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calculate <i>monthly payment</i> based on <i>monthlyRate</i>, <i>totalAmount</i> and <i>term</i> by formula:<br>
     * <i>annuityCoefficient</i> * <i>totalAmount</i><br>
     * where <i>annuityCoefficient</i> = (<i>monthlyRate</i> * (1 + <i>monthlyRate</i>) ^ <i>term</i>) / ((1 + <i>monthlyRate</i>) ^ <i>term</i> - 1)
     *
     * @param monthlyRate monthly rate
     * @param totalAmount oan body
     * @param term        loan term
     * @return calculated <i>monthly payment</i>
     */
    @Override
    public BigDecimal calculateMonthlyPayment(BigDecimal monthlyRate, BigDecimal totalAmount, Integer term) {
        BigDecimal numerator = monthlyRate.multiply(BigDecimal.ONE.add(monthlyRate).pow(term));
        BigDecimal denominator = BigDecimal.ONE.add(monthlyRate).pow(term).subtract(BigDecimal.ONE);

        BigDecimal annuityCoefficient = numerator.divide(denominator, PRECISION);
        return annuityCoefficient.multiply(totalAmount).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Add insurance price to loan body if insurance is enabled
     *
     * @param amount             initial loan body
     * @param isInsuranceEnabled specifies if insurance is enabled
     * @return new loan body
     */
    @Override
    public BigDecimal calculateLoanAmountBasedOnInsuranceStatus(BigDecimal amount, Boolean isInsuranceEnabled) {
        return isInsuranceEnabled ? amount.add(insurancePrice).setScale(2, RoundingMode.HALF_UP)
                : amount.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calculate total amount of money loaner should pay for the loan calculated by formula:<br>
     * monthlyPayment * term
     *
     * @param monthlyPayment amount of monthly payment
     * @param term           loan term
     * @return calculated total amount of paid money
     */
    @Override
    public BigDecimal calculateTotalAmount(BigDecimal monthlyPayment, Integer term) {
        return monthlyPayment.multiply(BigDecimal.valueOf(term)).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Check if loaner is able to loan. If not throw CreditDeniedException
     *
     * @param employmentData loaners employment status, position, work experience and salary
     * @param amount         loan body
     * @param birthDate      loaner's birthdate
     * @throws CreditDeniedException if loaner can not apply for the loan
     */
    @Override
    public void validateScoringData(EmploymentDto employmentData,
                                    BigDecimal amount,
                                    LocalDate birthDate
    ) {
        List<String> invalidDataDescription = new ArrayList<>();

        if (employmentData.getEmploymentStatus().equals(EmploymentStatus.UNEMPLOYED)) {
            invalidDataDescription.add("Unable to apply for the loan because of the employment status (UNEMPLOYED)");
        }

        if (amount.compareTo(employmentData.getSalary().multiply(TWENTY_TIMES)) > 0) {
            invalidDataDescription.add("Unable to apply for the loan because the amount is too large");
        }

        if (ChronoUnit.YEARS.between(birthDate, LocalDate.now()) < 20) {
            invalidDataDescription.add("Unable to apply for the loan because of the age (less than 20)");
        } else if (ChronoUnit.YEARS.between(birthDate, LocalDate.now()) > 60) {
            invalidDataDescription.add("Unable to apply for the loan because of the age (more than 60)");
        }

        if (employmentData.getWorkExperienceTotal() < 12 ||
                employmentData.getWorkExperienceCurrent() < 3) {
            invalidDataDescription.add("Unable to apply for the loan because the work experience is too low");
        }

        if (!invalidDataDescription.isEmpty()) {
            throw new CreditDeniedException(String.join(", ", invalidDataDescription));
        }
    }

    /**
     * Calculate credit rate based on scoring data
     *
     * @param scoringData loaner's scoring data
     * @return Calculated credit rate
     */
    @Override
    public BigDecimal calculateCreditRate(ScoringDataDto scoringData) {
        BigDecimal rate = new BigDecimal(basicRate.toString());

        rate = switch (scoringData.getEmployment().getEmploymentStatus()) {
            case SELF_EMPLOYED, EMPLOYED -> rate.add(BigDecimal.ONE);
            case BUSINESS_OWNER -> rate.add(THREE_PERCENT);
            default -> rate;
        };

        rate = switch (scoringData.getEmployment().getPosition()) {
            case WORKER, MIDDLE_MANAGER -> rate.subtract(TWO_PERCENT);
            case TOP_MANAGER -> rate.subtract(FOUR_PERCENT);
            case OWNER -> rate.subtract(THREE_PERCENT);
        };

        switch (scoringData.getMaritalStatus()) {
            case MARRIED -> rate = rate.subtract(THREE_PERCENT);
            case SINGLE -> {
            }
            case DIVORCED -> rate = rate.add(BigDecimal.ONE);
            case WIDOW_WIDOWER -> rate = rate.subtract(TWO_PERCENT);
        }

        if (scoringData.getDependentAmount() > 1) {
            rate = rate.add(BigDecimal.ONE);
        }

        switch (scoringData.getGender()) {
            case FEMALE -> {
                if (ChronoUnit.YEARS.between(scoringData.getBirthDate(), LocalDate.now()) >= 35 ||
                        ChronoUnit.YEARS.between(scoringData.getBirthDate(), LocalDate.now()) <= 60) {
                    rate = rate.subtract(THREE_PERCENT);
                }
            }
            case MALE -> {
                if (ChronoUnit.YEARS.between(scoringData.getBirthDate(), LocalDate.now()) >= 30 ||
                        ChronoUnit.YEARS.between(scoringData.getBirthDate(), LocalDate.now()) <= 55) {
                    rate = rate.subtract(THREE_PERCENT);
                }
            }
            case NON_BINARY -> rate = rate.add(THREE_PERCENT);
        }
        return rate.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calculate elements of payment schedule.<br>
     * <i>nextInterestPayment = remainingDebt * rate / 100 * lengthOfMonth / lengthOfYear</i><br>
     * <i>nextDebtPayment = monthlyPayment - nextInterestPayment</i><br>
     *
     * @param amount         loan body
     * @param term           loan term
     * @param rate           loan rate
     * @param monthlyPayment amount of money paid each month
     * @return list of payment schedule elements
     */
    @Override
    public List<PaymentScheduleElement> calculatePaymentSchedule(
            BigDecimal amount,
            Integer term,
            BigDecimal rate,
            BigDecimal monthlyPayment
    ) {
        List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();
        BigDecimal remainingDebt = amount;
        for (int i = 0; i < term; i++) {
            LocalDate nextPaymentDate = LocalDate.now().plusMonths(i + 1);
            BigDecimal nextInterestPayment = remainingDebt
                    .multiply(rate).divide(HUNDRED_PERCENT, PRECISION)
                    .multiply(BigDecimal.valueOf(nextPaymentDate.lengthOfMonth()))
                    .divide(BigDecimal.valueOf(nextPaymentDate.lengthOfYear()), PRECISION)
                    .setScale(2, RoundingMode.HALF_UP);
            BigDecimal nextDebtPayment = monthlyPayment.subtract(nextInterestPayment);
            remainingDebt = remainingDebt.subtract(nextDebtPayment).setScale(2, RoundingMode.HALF_UP);
            paymentSchedule.add(
                    PaymentScheduleElement.builder()
                            .number(i + 1)
                            .date(nextPaymentDate)
                            .interestPayment(nextInterestPayment)
                            .debtPayment(nextDebtPayment)
                            .totalPayment(monthlyPayment)
                            .remainingDebt(remainingDebt)
                            .build()
            );
        }
        return paymentSchedule;
    }

    /**
     * Calculate full loan price based on payment schedule and loan amount by formula:<br>
     * <i>full loan price = (total payment / amount - 1) * 100</i><br>
     * where <i>total payment</i> = sum of monthly payments of each payment schedule element
     *
     * @param paymentSchedule loan payment schedule
     * @param amount          loan amount
     * @return Calculated full loan price
     */
    @Override
    public BigDecimal calculatePsk(List<PaymentScheduleElement> paymentSchedule, BigDecimal amount) {
        BigDecimal totalPayment = paymentSchedule.stream()
                .map(PaymentScheduleElement::getTotalPayment)
                .reduce(BigDecimal::add)
                .orElseThrow();

        return totalPayment
                .divide(amount, PRECISION)
                .subtract(BigDecimal.ONE)
                .multiply(HUNDRED_PERCENT)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
