package kz.sweet.fit.models.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class AuthDTO {
    @NotEmpty
    String username;
    @NotEmpty
    String password;
}
