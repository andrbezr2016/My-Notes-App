package com.andrbezr2016.mynotes.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.andrbezr2016.mynotes.constants.ExceptionConstants.EXCEPTION_INCORRECT_PASSWORD;

@Data
public class LoginRequestDto {

    @NotNull
    @Size(min = 1, max = 254)
    @Email
    private String email;

    @NotNull
    @Size(min = 8, max = 256)
    @Pattern(regexp = "^\\S*$", message = EXCEPTION_INCORRECT_PASSWORD)
    private String password;
}
