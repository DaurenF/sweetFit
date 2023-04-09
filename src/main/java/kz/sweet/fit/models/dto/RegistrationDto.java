package kz.sweet.fit.models.dto;

import jakarta.validation.constraints.NotEmpty;


import kz.sweet.fit.models.enums.Sex;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegistrationDto {
    @NotEmpty
    String username;
    @NotEmpty
    String password;
    @NotEmpty
    String name;
    String lastname;
    @NotEmpty(message = "Email should not be empty")
    String email;
    Sex sex;
    String phone;
}

