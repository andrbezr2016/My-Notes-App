package com.andrbezr2016.mynotes.services;

import com.andrbezr2016.mynotes.contexts.RequestContext;
import com.andrbezr2016.mynotes.dto.UserDto;
import com.andrbezr2016.mynotes.dto.UserEditRequestDto;
import com.andrbezr2016.mynotes.entities.User;
import com.andrbezr2016.mynotes.exceptions.MyNotesAppException;
import com.andrbezr2016.mynotes.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;

import static com.andrbezr2016.mynotes.constants.ExceptionConstants.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RequestContext requestContext;

    public UserDto getCurrentUser() {
        User user = findCurrentUser();
        return toDto(user);
    }

    public UserDto editCurrentUser(UserEditRequestDto userEditRequestDto, MultipartFile icon) {
        User user = findCurrentUser();
        boolean isEdit = false;
        if (userEditRequestDto.getUsername() != null) {
            user.setUsername(userEditRequestDto.getUsername());
            isEdit = true;
        }
        if (userEditRequestDto.getPassword() != null) {
            user.setPassword(userEditRequestDto.getPassword());
            isEdit = true;
        }
        if (isEdit) {
            user.setModifiedAt(OffsetDateTime.now());
            user = userRepository.save(user);
            log.info("Modified user with id: " + requestContext.getUserId());
        }
        return toDto(user);
    }

    private User findCurrentUser() {
        return userRepository.findById(requestContext.getUserId()).orElseThrow(() -> {
            log.info("Not found user with id: " + requestContext.getUserId());
            return new MyNotesAppException(EXCEPTION_USER_NOT_FOUND);
        });
    }

    private UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .iconPath(user.getIconPath())
                .createdAt(user.getCreatedAt())
                .modifiedAt(user.getModifiedAt())
                .build();
    }
}
