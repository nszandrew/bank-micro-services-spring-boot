package br.com.nszandrew.exception.custom;

public class StatusAccountIsClosed extends RuntimeException {

    public StatusAccountIsClosed() {
        super("StatusAccountIsClosed not been usable");
    }

    public StatusAccountIsClosed(String message) {
        super(message);
    }
}

