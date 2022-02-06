package com.andrbezr2016.mynotes.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.andrbezr2016.mynotes.constants.ExceptionConstants.EXCEPTION_INCORRECT_PASSWORD;

@Data
public class UserEditRequestDto {

    @Size(min = 1, max = 256)
    private String username;

    @Size(min = 8, max = 256)
    @Pattern(regexp = "^\\S*$", message = EXCEPTION_INCORRECT_PASSWORD)
    private String password;
}
