package kz.sweet.fit.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDto {
    private String toEmail;
    private String subject;
    private String message;
}


