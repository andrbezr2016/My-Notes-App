package com.andrbezr2016.mynotes.dto;

import lombok.Data;

@Data
public class UserEditRequestDto {

    private String username;

    private String password;

    private String iconPath;
}
