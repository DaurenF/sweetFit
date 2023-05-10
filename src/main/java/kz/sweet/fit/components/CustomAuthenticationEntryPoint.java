package kz.sweet.fit.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kz.sweet.fit.exceptions.InvalidJwtTokenException;
import kz.sweet.fit.exceptions.MissingJwtTokenException;
import kz.sweet.fit.models.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component("customAuthenticationEntryPoint")
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        ErrorResponse errorResponse;
        int statusCode;

        log.error("Authentication failed", authException);

        if (authException instanceof InvalidJwtTokenException) {
            errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), authException.getMessage());
            statusCode = HttpServletResponse.SC_BAD_REQUEST;
        } else {
            errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.toString(), "Authentication failed");
            statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        }

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(statusCode);
        OutputStream responseStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseStream, errorResponse);
        responseStream.flush();
    }


}
