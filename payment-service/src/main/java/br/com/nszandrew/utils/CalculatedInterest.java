package br.com.nszandrew.utils;

import br.com.nszandrew.model.Payment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class CalculatedInterest {

    public BigDecimal calculateInterest(Payment payment) {
        long daysLate = ChronoUnit.DAYS.between(payment.getDueDate(), LocalDate.now());

        BigDecimal dailyInterestRate = payment.getInterestRate().divide(BigDecimal.valueOf(365), RoundingMode.HALF_UP);

        BigDecimal interest = payment.getInstallmentAmount().multiply(dailyInterestRate).multiply(BigDecimal.valueOf(daysLate));

        return interest;
    }
}
