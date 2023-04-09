package kz.sweet.fit.exceptions;

import kz.sweet.fit.models.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;

import java.io.StringWriter;
import java.io.PrintWriter;
import java.io.Writer;


@Component
@Slf4j
public class BaseExceptionHandler {

    public ResponseEntity<ErrorResponse> handleException(Exception e)
    {
        ErrorResponse errorResponse;
        HttpStatus status;
        if (e instanceof MethodArgumentNotValidException)
        {
            errorResponse = new ErrorResponse(e.getClass().getSimpleName(), "Wrong arguments");
            status = HttpStatus.BAD_REQUEST;
        }else
        if (e instanceof MissingRequestHeaderException)
        {
            errorResponse = new ErrorResponse(e.getClass().getSimpleName(), "Unauthorized");
            status = HttpStatus.UNAUTHORIZED;
        }else
        if (e instanceof BaseException)
        {
            BaseException baseException = (BaseException) e;
            errorResponse = new ErrorResponse(baseException.getClass().getSimpleName(), baseException.getMessage());
            status = HttpStatus.valueOf(baseException.getCode().intValue());
        }else {
            Writer buffer = new StringWriter();
            PrintWriter pw = new PrintWriter(buffer);
            e.printStackTrace(pw);
            log.error(e.toString()+" \r\n"+buffer.toString());
            errorResponse = new ErrorResponse("InternalServerErrorException", "Internal Server Error");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        log.info("Client Exception Response Code: "+"0"+status.value());
        log.info("Client Exception Response: "+ ObjectUtils.toString(errorResponse));
        return new ResponseEntity<>(errorResponse, status);
    }
}
