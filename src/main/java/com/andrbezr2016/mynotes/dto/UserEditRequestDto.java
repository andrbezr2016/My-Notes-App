package com.andrbezr2016.mynotes.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.andrbezr2016.mynotes.constants.ExceptionConstants.*;

@Data
public class UserEditRequestDto {

    @Size(min = 1, max = 256, message = EXCEPTION_SIZE)
    private String username;

    @Size(min = 8, max = 256, message = EXCEPTION_PASSWORD_SIZE)
    @Pattern(regexp = "^\\S*$", message = EXCEPTION_PASSWORD)
    private String password;
}
