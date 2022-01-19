package com.andrbezr2016.mynotes.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class UserTokenDto {

    private String accessToken;

    private String refreshToken;

    private long userId;

    private OffsetDateTime accessExpiredAt;

    private OffsetDateTime refreshExpiredAt;

    private OffsetDateTime createdAt;
}