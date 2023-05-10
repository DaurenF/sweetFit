package kz.sweet.fit.exceptions;

public class CouldNotCreateExerciseException extends BaseException{
    public CouldNotCreateExerciseException(String message, Integer code) {
        super(message, code);
    }
}
