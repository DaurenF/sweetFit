package kz.sweet.fit.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BaseException extends  RuntimeException{
    private String message;
    private int code;

}
