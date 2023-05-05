package kz.sweet.fit.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import kz.sweet.fit.exceptions.BaseExceptionHandler;
import kz.sweet.fit.exceptions.IncorrectCredentialsException;
import kz.sweet.fit.models.dto.ErrorResponse;
import kz.sweet.fit.models.UserEntity;
import kz.sweet.fit.models.dto.LoginDto;
import kz.sweet.fit.models.dto.RegistrationDto;
import kz.sweet.fit.security.JWTUtil;
import kz.sweet.fit.services.RegistrationService;
import kz.sweet.fit.util.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@Slf4j
public class AuthorizationController {
    private final RegistrationService registrationService;
    private final UserValidator userValidator;
    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;
    private final BaseExceptionHandler exceptionHandler;

    public AuthorizationController(RegistrationService registrationService, UserValidator userValidator, JWTUtil jwtUtil,
                                   ObjectMapper objectMapper, AuthenticationManager authenticationManager, BaseExceptionHandler exceptionHandler) {
        this.registrationService = registrationService;
        this.userValidator = userValidator;
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
        this.authenticationManager = authenticationManager;
        this.exceptionHandler = exceptionHandler;
    }

    @GetMapping("/healthcheck")
    public ResponseEntity<String> healthCheck() {
        log.info("Health check");
        registrationService.longTask();
        log.warn("Long task finished");
        Map<String, String> map = new HashMap<>();
        map.put("status", "OK");
        map.put("CI-CD", "OK");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @GetMapping("/jwt")
    public ResponseEntity<String> jwt() {
        log.info("JWT check");
        Map<String, String> map = new HashMap<>();
        map.put("JWT", "OK");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @PostMapping(value = "/registration", consumes = "application/json")
    public Map<String, String> performRegistration(@RequestBody @Valid RegistrationDto registrationDto,
                                                   BindingResult bindingResult) throws JsonProcessingException {
        log.info("Registration request: {}", registrationDto.toString());
        UserEntity user = convertRegDtoToPerson(registrationDto);
        log.info("User: {}", objectMapper.writeValueAsString(user.toString()));

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return Map.of("message", "Ошибка!");
        }

        registrationService.register(user);

        String token = jwtUtil.generateToken(user.getUsername());
        return Map.of("token", token);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> performLogin(@RequestBody LoginDto authenticationDTO) {
        log.info("Login request: {}", authenticationDTO.toString());
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                        authenticationDTO.getPassword());
        log.info("AuthenticationManager");

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            throw new IncorrectCredentialsException("Incorrect credentials!", HttpStatus.UNAUTHORIZED.value());
        }

        log.info("Successful login: {}", authenticationDTO.getUsername());
        String token = jwtUtil.generateToken(authenticationDTO.getUsername());
        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/check-username")
    public ResponseEntity<Map<String, Boolean>> checkUsernameUnique(@RequestParam("username") String username) {
        log.info("Client request check-username");
        return ResponseEntity.ok(Map.of("exists", registrationService.checkUsernameExists(username)));
    }

    public UserEntity convertRegDtoToPerson(RegistrationDto registrationDto) {
        return this.objectMapper.convertValue(registrationDto, UserEntity.class);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return exceptionHandler.handleException(e);
    }


}
