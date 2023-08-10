package com.sunshard.conveyor.service.impl;

import com.sunshard.conveyor.exception.CreditDeniedException;
import com.sunshard.conveyor.model.EmploymentDTO;
import com.sunshard.conveyor.model.PaymentScheduleElement;
import com.sunshard.conveyor.model.ScoringDataDTO;
import com.sunshard.conveyor.model.enums.EmploymentStatus;
import com.sunshard.conveyor.service.ScoringService;
import com.sunshard.conveyor.service.util.CalculationParameters;
import com.sunshard.conveyor.service.util.CreditProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.sunshard.conveyor.service.util.CalculationParameters.*;

/**
 * Service for encapsulating <i>loan offer</i> and <i>credit data</i> parameters calculations
 */
@Service
@RequiredArgsConstructor
public class ScoringServiceImpl implements ScoringService {

    private final CreditProperties creditProperties;

    /**
     * Calculate <i>loan rate</i> based on <i>isInsuranceEnabled</i> and <i>isSalaryClient</i>.<br>
     * If <i>isInsuranceEnabled</i> is <i>true</i> rate is decreased by 3.<br>
     * If <i>isSalaryClient</i> is <i>true</i> rate is decreased by 1.<br>
     * @param isInsuranceEnabled specifies if insurance is enabled for <i>loan offer</i>
     * @param isSalaryClient specifies if loaner is a salary client
     * @return Calculated <i>loan rate</i>
     */
    @Override
    public BigDecimal calculateRate(Boolean isInsuranceEnabled, Boolean isSalaryClient) {
        BigDecimal currentRate = creditProperties.getBasicRate();
        BigDecimal rate = isInsuranceEnabled ? currentRate.subtract(THREE) : currentRate;
        return isSalaryClient ? rate.subtract(BigDecimal.ONE) : rate;
    }

    /**
     * Calculate <i>monthly rate</i> based on <i>loan rate</i> by formula:<br>
     * rate / (12 * 100)
     * @param rate received <i>loan rate</i>
     * @return calculated <i>monthly rate</i>
     */
    @Override
    public BigDecimal calculateMonthlyRate(BigDecimal rate) {
        return rate.divide(CalculationParameters.HUNDRED.multiply(TWELVE), SCALE);
    }

    /**
     * Calculate <i>monthly payment</i> based on <i>monthlyRate</i>, <i>totalAmount</i> and <i>term</i> by formula:<br>
     * <i>annuityCoefficient</i> * <i>totalAmount</i><br>
     * where <i>annuityCoefficient</i> = (<i>monthlyRate</i> * (1 + <i>monthlyRate</i>) ^ <i>term</i>) / ((1 + <i>monthlyRate</i>) ^ <i>term</i> - 1)
     * @param monthlyRate monthly rate
     * @param totalAmount oan body
     * @param term loan term
     * @return calculated <i>monthly payment</i>
     */
    @Override
    public BigDecimal calculateMonthlyPayment(BigDecimal monthlyRate, BigDecimal totalAmount, Integer term) {
        BigDecimal numerator = monthlyRate.multiply(BigDecimal.ONE.add(monthlyRate).pow(term));
        BigDecimal denominator = BigDecimal.ONE.add(monthlyRate).pow(term).subtract(BigDecimal.ONE);

        BigDecimal annuityCoefficient = numerator.divide(denominator, SCALE);
        return annuityCoefficient.multiply(totalAmount);
    }

    /**
     * Add insurance price to loan body if insurance is enabled
     * @param amount initial loan body
     * @param isInsuranceEnabled specifies if insurance is enabled
     * @return new loan body
     */
    @Override
    public BigDecimal calculateLoanAmountBasedOnInsuranceStatus(BigDecimal amount, Boolean isInsuranceEnabled) {
        return isInsuranceEnabled ? amount.add(creditProperties.getInsurancePrice()) : amount;
    }

    /**
     * Calculate total amount of money loaner should pay for the loan calculated by formula:<br>
     * monthlyPayment * term
     * @param monthlyPayment amount of monthly payment
     * @param term loan term
     * @return calculated total amount of paid money
     */
    @Override
    public BigDecimal calculateTotalAmount(BigDecimal monthlyPayment, Integer term) {
        return monthlyPayment.multiply(BigDecimal.valueOf(term));
    }

    /**
     * Check if loaner is able to loan. If not throw CreditDeniedException
     * @param employmentData loaners employment status, position, work experience and salary
     * @param amount loan body
     * @param birthDate loaner's birthdate
     * @throws CreditDeniedException if loaner can not apply for the loan
     */
    @Override
    public void validateScoringData(EmploymentDTO employmentData,
                                    BigDecimal amount,
                                    LocalDate birthDate
    ) throws CreditDeniedException {
        List<String> invalidDataDescription = new ArrayList<>();

        if (employmentData.getEmploymentStatus().equals(EmploymentStatus.UNEMPLOYED)) {
            invalidDataDescription.add("Unable to apply for the loan because of the employment status (UNEMPLOYED)");
        }

        if (amount.compareTo(employmentData.getSalary().multiply(TWENTY)) > 0) {
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
            throw new CreditDeniedException(invalidDataDescription.get(0));
        }
    }

    /**
     * Calculate credit rate based on scoring data
     * @param scoringData loaner's scoring data
     * @return Calculated credit rate
     */
    @Override
    public BigDecimal calculateCreditRate(ScoringDataDTO scoringData) {
        BigDecimal rate = new BigDecimal(creditProperties.getBasicRate().toString());

        switch (scoringData.getEmployment().getEmploymentStatus()) {
            case SELF_EMPLOYED:
            case EMPLOYED:
                rate = rate.add(BigDecimal.ONE);
                break;
            case BUSINESS_OWNER:
                rate = rate.add(THREE);
                break;
        }

        switch (scoringData.getEmployment().getPosition()) {
            case WORKER:
            case MIDDLE_MANAGER:
                rate = rate.subtract(TWO);
                break;
            case TOP_MANAGER:
                rate = rate.subtract(FOUR);
                break;
            case OWNER:
                rate = rate.subtract(THREE);
        }

        switch (scoringData.getMaritalStatus()) {
            case MARRIED:
                rate = rate.subtract(THREE);
                break;
            case SINGLE:
                break;
            case DIVORCED:
                rate = rate.add(BigDecimal.ONE);
                break;
            case WIDOW_WIDOWER:
                rate = rate.subtract(TWO);
        }

        if (scoringData.getDependentAmount() > 1) {
            rate = rate.add(BigDecimal.ONE);
        }

        switch (scoringData.getGender()) {
            case FEMALE:
                if (ChronoUnit.YEARS.between(scoringData.getBirthDate(), LocalDate.now()) >= 35 ||
                        ChronoUnit.YEARS.between(scoringData.getBirthDate(), LocalDate.now()) <= 60) {
                    rate = rate.subtract(THREE);
                }
                break;
            case MALE:
                if (ChronoUnit.YEARS.between(scoringData.getBirthDate(), LocalDate.now()) >= 30 ||
                        ChronoUnit.YEARS.between(scoringData.getBirthDate(), LocalDate.now()) <= 55) {
                    rate = rate.subtract(THREE);
                }
                break;
            case NON_BINARY:
                rate = rate.add(THREE);
                break;
        }
        return rate;
    }

    /**
     * Calculate elements of payment schedule.<br>
     * <i>nextInterestPayment = remainingDebt * rate / 100 * lengthOfMonth / lengthOfYear</i><br>
     * <i>nextDebtPayment = monthlyPayment - nextInterestPayment</i><br>
     * @param amount loan body
     * @param term loan term
     * @param rate loan rate
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
                    .multiply(rate).divide(HUNDRED, SCALE)
                    .multiply(BigDecimal.valueOf(nextPaymentDate.lengthOfMonth()))
                    .divide(BigDecimal.valueOf(nextPaymentDate.lengthOfYear()), SCALE);
            BigDecimal nextDebtPayment = monthlyPayment.subtract(nextInterestPayment);
            remainingDebt = remainingDebt.subtract(nextDebtPayment);
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
     * @param paymentSchedule loan payment schedule
     * @param amount loan amount
     * @return Calculated full loan price
     */
    @Override
    public BigDecimal calculatePsk(List<PaymentScheduleElement> paymentSchedule, BigDecimal amount) {
        BigDecimal totalPayment = paymentSchedule.stream()
                .map(PaymentScheduleElement::getTotalPayment)
                .reduce(BigDecimal::add)
                .orElseThrow();

        return totalPayment
                .divide(amount, SCALE)
                .subtract(BigDecimal.ONE)
                .multiply(HUNDRED);
    }
}
