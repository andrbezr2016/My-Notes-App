package com.andrbezr2016.mynotes.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.andrbezr2016.mynotes.constants.ExceptionConstants.*;

@Data
public class RegistrationRequestDto {

    @NotNull(message = EXCEPTION_NOT_NULL)
    @Size(min = 1, max = 256, message = EXCEPTION_SIZE)
    private String username;

    @NotNull(message = EXCEPTION_NOT_NULL)
    @Size(min = 1, max = 254, message = EXCEPTION_EMAIL_SIZE)
    @Email(message = EXCEPTION_EMAIL)
    private String email;

    @NotNull(message = EXCEPTION_NOT_NULL)
    @Size(min = 8, max = 256, message = EXCEPTION_PASSWORD_SIZE)
    @Pattern(regexp = "^\\S*$", message = EXCEPTION_PASSWORD)
    private String password;
}
