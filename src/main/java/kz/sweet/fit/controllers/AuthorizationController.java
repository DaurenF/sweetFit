package kz.sweet.fit.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import kz.sweet.fit.models.UserEntity;
import kz.sweet.fit.models.dto.LoginDto;
import kz.sweet.fit.models.dto.RegistrationDto;
import kz.sweet.fit.security.JWTUtil;
import kz.sweet.fit.services.RegistrationService;
import kz.sweet.fit.util.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@Slf4j
public class AuthorizationController {
    @Autowired
    RegistrationService registrationService;
    @Autowired
    UserValidator userValidator;
    @Autowired
    JWTUtil jwtUtil;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    AuthenticationManager authenticationManager;
    @GetMapping("/healthcheck")
    public ResponseEntity<String> healthCheck(){
        log.info("Health check");
        Map map = new HashMap<String, String>();
        map.put("status", "OK");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @GetMapping("/jwt")
    public ResponseEntity<String> jwt(){
        log.info("JWT check");
        Map map = new HashMap<String, String>();
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
            System.out.println(bindingResult.getAllErrors().toString());
            return Map.of("message", "Ошибка!");
        }

        registrationService.register(user);

        String token = jwtUtil.generateToken(user.getUsername());
        return Map.of("jwt-token", token);
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
            return ResponseEntity.badRequest().body(Map.of("message", "Incorrect credentials!"));
        }

        log.info("Successful login: {}", authenticationDTO.getUsername());
        String token = jwtUtil.generateToken(authenticationDTO.getUsername());
        return ResponseEntity.ok(Map.of("jwt-token", token));
    }

    public UserEntity convertRegDtoToPerson(RegistrationDto registrationDto) {
        return this.objectMapper.convertValue(registrationDto, UserEntity.class);
    }

}
