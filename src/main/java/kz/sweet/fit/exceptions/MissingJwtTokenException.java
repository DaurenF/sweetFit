package kz.sweet.fit.exceptions;

public class MissingJwtTokenException extends RuntimeException {
    public MissingJwtTokenException(String message) {
        super(message);
    }
}