package com.andrbezr2016.mynotes.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserRefreshTokenDto {

    private String token;

    private long userId;

    private Timestamp createdAt;

    private Timestamp expiredAt;
}
