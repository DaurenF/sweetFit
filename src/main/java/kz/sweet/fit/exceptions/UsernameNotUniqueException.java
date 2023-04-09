package kz.sweet.fit.exceptions;

public class UsernameNotUniqueException extends BaseException{
    public UsernameNotUniqueException(String message, Integer code) {
        super(message, code);
    }
}
