package com.andrbezr2016.mynotes.services;

import com.andrbezr2016.mynotes.dto.UserDto;
import com.andrbezr2016.mynotes.dto.UserEditRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

    public UserDto getCurrentUser() {
        return null;
    }

    public UserDto editCurrentUser(UserEditRequestDto user, MultipartFile icon) {
        return null;
    }
}
