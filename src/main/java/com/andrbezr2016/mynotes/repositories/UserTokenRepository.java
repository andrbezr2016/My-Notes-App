package com.andrbezr2016.mynotes.repositories;

import com.andrbezr2016.mynotes.entities.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    UserToken findByAccessToken(String accessToken);

    UserToken findByRefreshToken(String refreshToken);

    boolean existsByAccessTokenOrRefreshToken(String accessToken, String refreshToken);
}
