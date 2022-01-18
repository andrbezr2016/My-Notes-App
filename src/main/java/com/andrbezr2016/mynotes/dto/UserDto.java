package com.andrbezr2016.mynotes.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class UserDto {

    private long id;

    private String username;

    private String email;

    private String password;

    private String iconPath;

    private Date createdAt;

    private Date modifiedAt;
}
