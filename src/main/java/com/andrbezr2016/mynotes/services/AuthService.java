package com.andrbezr2016.mynotes.services;

import com.andrbezr2016.mynotes.dto.LoginRequestDto;
import com.andrbezr2016.mynotes.dto.RegistrationRequestDto;
import com.andrbezr2016.mynotes.dto.UserDto;
import com.andrbezr2016.mynotes.dto.UserTokenDto;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public UserTokenDto login(LoginRequestDto loginRequestDto) {
        return null;
    }

    public UserDto register(RegistrationRequestDto registrationRequestDto) {
        return null;
    }

    public UserTokenDto requestToken(String refreshToken) {
        return null;
    }
}
