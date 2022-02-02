package com.andrbezr2016.mynotes.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class UserTokenDto {

    private String accessToken;

    private String refreshToken;

    private Long userId;

    private OffsetDateTime accessExpiredAt;

    private OffsetDateTime refreshExpiredAt;

    private OffsetDateTime createdAt;
}
