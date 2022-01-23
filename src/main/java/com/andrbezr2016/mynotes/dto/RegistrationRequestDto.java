package com.andrbezr2016.mynotes.dto;

import lombok.Data;

@Data
public class RegistrationRequestDto {

    private String username;

    private String email;

    private String password;

    private String iconPath;
}
