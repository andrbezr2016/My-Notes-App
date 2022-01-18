package com.andrbezr2016.mynotes.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Data
@Entity
@Table(name = "user_access_tokens")
public class UserAccessToken {

    @Id
    @Column(name = "token")
    private String token;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "expired_at")
    private Date expiredAt;
}
