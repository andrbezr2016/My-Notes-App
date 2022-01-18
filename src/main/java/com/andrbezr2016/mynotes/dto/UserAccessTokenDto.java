package com.andrbezr2016.mynotes.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class UserAccessTokenDto {

    private String token;

    private long userId;

    private Date createdAt;

    private Date expiredAt;
}
