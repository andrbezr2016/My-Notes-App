package com.andrbezr2016.mynotes.dto;

import lombok.Data;

@Data
public class RegisterRequestDto {

    private String username;

    private String password;

    private String email;

    private String iconPath;
}
