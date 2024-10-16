package br.com.nszandrew.exception.custom;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException() {
        super("Account not found");
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}
