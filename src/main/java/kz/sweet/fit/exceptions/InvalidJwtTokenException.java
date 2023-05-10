package kz.sweet.fit.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtTokenException extends AuthenticationException {
    private String errorMessage;

    public InvalidJwtTokenException(String message) {
        super(message);
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}