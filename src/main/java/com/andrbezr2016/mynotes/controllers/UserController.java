package com.andrbezr2016.mynotes.controllers;

import com.andrbezr2016.mynotes.dto.UserDto;
import com.andrbezr2016.mynotes.dto.UserEditRequestDto;
import com.andrbezr2016.mynotes.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static com.andrbezr2016.mynotes.constants.ApiConstants.API_USER_PATH;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping(API_USER_PATH)
public class UserController {

    private final UserService userService;

    @GetMapping
    public UserDto getCurrentUser() {
        return userService.getCurrentUser();
    }

    @PatchMapping
    public UserDto editCurrentUser(@RequestPart @Valid UserEditRequestDto userEditRequestDto, @RequestPart(required = false) MultipartFile icon) {
        return userService.editCurrentUser(userEditRequestDto, icon);
    }
}
