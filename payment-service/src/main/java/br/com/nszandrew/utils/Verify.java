package br.com.nszandrew.utils;

import br.com.nszandrew.model.Payment;
import br.com.nszandrew.model.PaymentType;
import br.com.nszandrew.model.Status;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Verify {
    public void extracted(Payment payment) {
        if (payment.getPaymentDate().isBefore(LocalDate.now())) {
            payment.setStatus(Status.LATE);
        }

        if (!(payment.getCurrentInstallments() == null) && payment.getPaymentType() == PaymentType.CREDIT) {
            payment.setCurrentInstallments(payment.getCurrentInstallments() + 1);
        } else {
            payment.setCurrentInstallments(1);
        }

        if (payment.getCurrentInstallments() >= payment.getInstallments() && payment.getPaymentType() == PaymentType.CREDIT) {
            payment.setStatus(Status.PAID);
        } else if (payment.getPaymentType() == PaymentType.CREDIT) {
            payment.setStatus(Status.PENDING);
        } else if (payment.getPaymentType() == PaymentType.DEBIT) {
            payment.setStatus(Status.PAID);
        }
    }
}
