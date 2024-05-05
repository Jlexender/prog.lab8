package ru.lexender.springcrud8.command.exception;

public class CommandAbbreviationCollisionException extends RuntimeException {
    public CommandAbbreviationCollisionException(String message) {
        super(message);
    }

    public CommandAbbreviationCollisionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandAbbreviationCollisionException(Throwable cause) {
        super(cause);
    }

    public CommandAbbreviationCollisionException() {
        super();
    }
}
