package kz.sweet.fit.models.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginDto {
    @NotEmpty
    String username;
    @NotEmpty
    String password;
}
