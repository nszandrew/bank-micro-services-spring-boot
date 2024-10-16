package br.com.nszandrew.exception.custom;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException() {
        super("Insufficient Funds");
    }

    public InsufficientFundsException(String message) {
        super(message);
    }
}
