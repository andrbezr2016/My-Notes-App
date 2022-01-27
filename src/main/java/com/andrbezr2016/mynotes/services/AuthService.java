package com.andrbezr2016.mynotes.services;

import com.andrbezr2016.mynotes.dto.LoginRequestDto;
import com.andrbezr2016.mynotes.dto.RegistrationRequestDto;
import com.andrbezr2016.mynotes.dto.UserTokenDto;
import com.andrbezr2016.mynotes.entities.User;
import com.andrbezr2016.mynotes.entities.UserToken;
import com.andrbezr2016.mynotes.exceptions.MyNotesAppException;
import com.andrbezr2016.mynotes.repositories.UserRepository;
import com.andrbezr2016.mynotes.repositories.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

import static com.andrbezr2016.mynotes.constants.ApiConstants.*;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;

    public UserTokenDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail());
        if (user != null && loginRequestDto.getPassword().equals(user.getPassword())) {
            return genUserToken(user.getId());
        } else {
            throw new MyNotesAppException(EXCEPTION_INVALID_USER);
        }
    }

    public void register(RegistrationRequestDto registrationRequestDto) {
        boolean isTrue = userRepository.existsByEmail(registrationRequestDto.getEmail());
        if (!isTrue) {
            OffsetDateTime currentTime = OffsetDateTime.now();
            userRepository.save(User.builder()
                    .username(registrationRequestDto.getUsername())
                    .email(registrationRequestDto.getEmail())
                    .password(registrationRequestDto.getPassword())
                    .createdAt(currentTime)
                    .modifiedAt(currentTime)
                    .build());
        } else {
            throw new MyNotesAppException(EXCEPTION_EXISTING_USER);
        }
    }

    public UserTokenDto requestToken(String refreshToken) {
        UserToken userToken = userTokenRepository.findByRefreshToken(refreshToken);
        if (userToken != null && userToken.getRefreshExpiredAt().isAfter(OffsetDateTime.now())) {
            return genUserToken(userToken.getUserId());
        } else {
            throw new MyNotesAppException(EXCEPTION_INVALID_TOKEN);
        }
    }

    public void logout() {
    }

    private UserTokenDto genUserToken(long userId) {
        String accessToken;
        String refreshToken;
        do {
            accessToken = DigestUtils.sha256Hex(UUID.randomUUID().toString());
            refreshToken = DigestUtils.sha256Hex(UUID.randomUUID().toString());
        } while (userTokenRepository.existsByAccessTokenOrRefreshToken(accessToken, refreshToken));
        OffsetDateTime currentTime = OffsetDateTime.now();
        UserToken userToken = UserToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(userId)
                .accessExpiredAt(currentTime.plusMinutes(ACCESS_EXPIRED_IN_MINUTES))
                .refreshExpiredAt(currentTime.plusMinutes(REFRESH_EXPIRED_IN_MINUTES))
                .createdAt(currentTime)
                .build();
        userTokenRepository.save(userToken);
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
