package br.com.nszandrew.exceptions.custom;

public class PaymentAlreadyCompletedException extends RuntimeException {

    public PaymentAlreadyCompletedException() {
        super("Payment already completed");
    }

    public PaymentAlreadyCompletedException(String message) {
        super(message);
    }

}
