package com.andrbezr2016.mynotes.controllers;

import com.andrbezr2016.mynotes.dto.LoginRequestDto;
import com.andrbezr2016.mynotes.dto.RegistrationRequestDto;
import com.andrbezr2016.mynotes.dto.UserTokenDto;
import com.andrbezr2016.mynotes.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static com.andrbezr2016.mynotes.constants.ApiConstants.API_AUTH_PATH;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(API_AUTH_PATH)
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public UserTokenDto login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }

    @PostMapping("/registration")
    public void register(@RequestBody @Valid RegistrationRequestDto registrationRequestDto) {
        authService.register(registrationRequestDto);
    }

    @GetMapping("/token")
    public UserTokenDto requestToken(@RequestParam @NotBlank String refreshToken) {
        return authService.requestToken(refreshToken);
    }

    @PostMapping("/logout")
    public void logout() {
        authService.logout();
    }
}
