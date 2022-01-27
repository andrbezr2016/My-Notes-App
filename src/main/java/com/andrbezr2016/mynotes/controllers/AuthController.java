package com.andrbezr2016.mynotes.controllers;

import com.andrbezr2016.mynotes.dto.LoginRequestDto;
import com.andrbezr2016.mynotes.dto.RegistrationRequestDto;
import com.andrbezr2016.mynotes.dto.UserTokenDto;
import com.andrbezr2016.mynotes.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.andrbezr2016.mynotes.constants.ApiConstants.API_AUTH_PATH;

@RequiredArgsConstructor
@RestController
@RequestMapping(API_AUTH_PATH)
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public UserTokenDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }

    @PostMapping("/registration")
    public void register(@RequestBody RegistrationRequestDto registrationRequestDto) {
        authService.register(registrationRequestDto);
    }

    @GetMapping("/token")
    public UserTokenDto requestToken(@RequestParam String refreshToken) {
        return authService.requestToken(refreshToken);
    }

    @PostMapping("/logout")
    public void logout() {
        authService.logout();
    }
}
