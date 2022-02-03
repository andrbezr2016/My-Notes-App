package com.andrbezr2016.mynotes.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_tokens")
public class UserToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "access_token", unique = true, nullable = false)
    private String accessToken;

    @Column(name = "refresh_token", unique = true, nullable = false)
    private String refreshToken;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "access_expired_at")
    private OffsetDateTime accessExpiredAt;

    @Column(name = "refresh_expired_at")
    private OffsetDateTime refreshExpiredAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}
