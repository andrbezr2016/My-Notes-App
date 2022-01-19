package com.andrbezr2016.mynotes.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "user_refresh_tokens")
public class UserRefreshToken {

    @Id
    @Column(name = "token")
    private String token;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "expired_at")
    private Timestamp expiredAt;
}
