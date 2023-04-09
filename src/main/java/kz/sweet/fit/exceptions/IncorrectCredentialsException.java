package kz.sweet.fit.exceptions;

public class IncorrectCredentialsException extends BaseException{
    public IncorrectCredentialsException(String message, Integer code) {
        super(message, code);
    }
}
