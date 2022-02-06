package com.andrbezr2016.mynotes.services;

import com.andrbezr2016.mynotes.configuration.ConfigProperties;
import com.andrbezr2016.mynotes.contexts.RequestContext;
import com.andrbezr2016.mynotes.dto.LoginRequestDto;
import com.andrbezr2016.mynotes.dto.RegistrationRequestDto;
import com.andrbezr2016.mynotes.dto.UserTokenDto;
import com.andrbezr2016.mynotes.entities.User;
import com.andrbezr2016.mynotes.entities.UserToken;
import com.andrbezr2016.mynotes.exceptions.AuthorizationException;
import com.andrbezr2016.mynotes.exceptions.MyNotesAppException;
import com.andrbezr2016.mynotes.repositories.UserRepository;
import com.andrbezr2016.mynotes.repositories.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

import static com.andrbezr2016.mynotes.constants.ExceptionConstants.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    private final RequestContext requestContext;
    private final ConfigProperties properties;

    public UserTokenDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail());
        if (user != null && user.getPassword().equals(loginRequestDto.getPassword())) {
            return genUserToken(user.getId());
        } else {
            log.warn("Failed login");
            throw new MyNotesAppException(EXCEPTION_INVALID_USER);
        }
    }

    public void register(RegistrationRequestDto registrationRequestDto) {
        boolean isTrue = userRepository.existsByEmail(registrationRequestDto.getEmail());
        if (!isTrue) {
            User user = userRepository.save(User.builder()
                    .username(registrationRequestDto.getUsername())
                    .email(registrationRequestDto.getEmail())
                    .password(registrationRequestDto.getPassword())
                    .build());
            log.debug("Added user with id: " + user.getId());
        } else {
            log.warn("Failed registration");
            throw new MyNotesAppException(EXCEPTION_EXISTING_USER);
        }
    }

    public UserTokenDto requestToken(String refreshToken) {
        UserToken userToken = userTokenRepository.findByRefreshToken(refreshToken);
        if (userToken != null && userToken.getRefreshExpiredAt().isAfter(OffsetDateTime.now())) {
            return genUserToken(userToken.getUserId());
        } else {
            log.warn("Failed refresh token");
            throw new MyNotesAppException(EXCEPTION_INVALID_TOKEN);
        }
    }

    public void logout() {
        userTokenRepository.deleteByAccessToken(requestContext.getAccessToken());
        log.debug("Removed token for user with id: " + requestContext.getUserId());
    }

    public UserToken checkUserToken(String accessToken) {
        UserToken userToken = userTokenRepository.findByAccessToken(accessToken);
        if (userToken != null && userToken.getAccessExpiredAt().isAfter(OffsetDateTime.now())) {
            log.debug("Successful authorization for user with id: " + userToken.getUserId());
            return userToken;
        } else {
            log.warn("Failed authorization");
            throw new AuthorizationException(EXCEPTION_UNAUTHORIZED);
        }
    }

    private UserTokenDto genUserToken(Long userId) {
        String accessToken = DigestUtils.sha256Hex(UUID.randomUUID().toString());
        String refreshToken = DigestUtils.sha256Hex(UUID.randomUUID().toString());
        OffsetDateTime currentTime = OffsetDateTime.now();
        UserToken userToken = UserToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(userId)
                .accessExpiredAt(currentTime.plusMinutes(properties.getAccessExpiredIn()))
                .refreshExpiredAt(currentTime.plusMinutes(properties.getRefreshExpiredIn()))
                .build();
        userTokenRepository.deleteByUserId(userId);
        userTokenRepository.save(userToken);
        log.debug("Generated token for user with id: " + userId);
        return UserTokenDto.builder()
                .accessToken(userToken.getAccessToken())
                .refreshToken(userToken.getRefreshToken())
                .userId(userToken.getUserId())
                .accessExpiredAt(userToken.getAccessExpiredAt())
                .refreshExpiredAt(userToken.getRefreshExpiredAt())
                .createdAt(userToken.getCreatedAt())
                .build();
    }
}
