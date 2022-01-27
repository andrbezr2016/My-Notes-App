package com.andrbezr2016.mynotes.repositories;

import com.andrbezr2016.mynotes.entities.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    UserToken findByAccessToken(String accessToken);

    UserToken findByRefreshToken(String refreshToken);

    @Transactional
    void deleteByUserId(Long userId);

    @Transactional
    void deleteByAccessToken(String accessToken);
}
