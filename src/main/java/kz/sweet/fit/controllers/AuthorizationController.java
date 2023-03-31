package kz.sweet.fit.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import kz.sweet.fit.models.UserEntity;
import kz.sweet.fit.models.dto.AuthDTO;
import kz.sweet.fit.security.JWTUtil;
import kz.sweet.fit.services.RegistrationService;
import kz.sweet.fit.util.UserValidator;
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
        Map map = new HashMap<String, String>();
        map.put("status", "OK");
        return new ResponseEntity(map, HttpStatus.OK);
    }
    @PostMapping(value = "/registration", consumes = "application/json")
    public Map<String, String> performRegistration(@RequestBody @Valid AuthDTO authDTO,
                                                   BindingResult bindingResult) {
        UserEntity user = convertToPerson(authDTO);


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
    public ResponseEntity<Map<String, String>> performLogin(@RequestBody AuthDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                        authenticationDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Incorrect credentials!"));
        }

        String token = jwtUtil.generateToken(authenticationDTO.getUsername());
        return ResponseEntity.ok(Map.of("jwt-token", token));
    }


    public UserEntity convertToPerson(AuthDTO personDTO) {
        return this.objectMapper.convertValue(personDTO, UserEntity.class);
    }




}
