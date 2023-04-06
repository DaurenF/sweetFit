package kz.sweet.fit.exceptions;

public class UserNotFoundException extends BaseException{
    public UserNotFoundException(String message, int code) {
        super(message, code);
    }
}
