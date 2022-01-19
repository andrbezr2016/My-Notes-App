package com.andrbezr2016.mynotes.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "user_tokens")
public class UserToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "access_token", unique = true, nullable = false)
    private String accessToken;

    @Column(name = "refresh_token", unique = true, nullable = false)
    private String refreshToken;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "access_expired_at")
    private OffsetDateTime accessExpiredAt;

    @Column(name = "refresh_expired_at")
    private OffsetDateTime refreshExpiredAt;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}
