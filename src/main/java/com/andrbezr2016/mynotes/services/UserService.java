package com.andrbezr2016.mynotes.services;

import com.andrbezr2016.mynotes.configuration.ConfigProperties;
import com.andrbezr2016.mynotes.contexts.RequestContext;
import com.andrbezr2016.mynotes.dto.UserDto;
import com.andrbezr2016.mynotes.dto.UserEditRequestDto;
import com.andrbezr2016.mynotes.entities.User;
import com.andrbezr2016.mynotes.exceptions.MyNotesAppException;
import com.andrbezr2016.mynotes.exceptions.StorageException;
import com.andrbezr2016.mynotes.repositories.UserRepository;
import com.andrbezr2016.mynotes.utilities.BCryptEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.andrbezr2016.mynotes.constants.ExceptionConstants.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RequestContext requestContext;
    private final ConfigProperties properties;
    private final BCryptEncoder bCrypt;

    public UserDto getCurrentUser() {
        User user = findCurrentUser();
        return toDto(user);
    }

    public UserDto editCurrentUser(UserEditRequestDto userEditRequestDto, MultipartFile icon) {
        User user = findCurrentUser();
        boolean isEdit = false;
        if (userEditRequestDto.getUsername() != null && !userEditRequestDto.getUsername().equals(user.getUsername())) {
            user.setUsername(userEditRequestDto.getUsername());
            isEdit = true;
        }
        if (userEditRequestDto.getPassword() != null && !bCrypt.check(userEditRequestDto.getPassword(), user.getPassword())) {
            user.setPassword(bCrypt.encode(userEditRequestDto.getPassword()));
            isEdit = true;
        }
        if (icon != null && !icon.isEmpty()) {
            if (icon.getContentType().equals("image/jpeg")) {
                try {
                    Files.createDirectories(Paths.get(properties.getStorageLocation()));
                    Path file = Paths.get(properties.getStorageLocation(), requestContext.getUserId() + ".jpg");
                    ImageIO.write(ImageIO.read(icon.getInputStream()), "jpg", file.toFile());
                    isEdit = true;
                } catch (IOException e) {
                    throw new StorageException(e);
                }
            } else {
                log.warn("Invalid upload file content-type: " + icon.getContentType() + " for user with id: " + requestContext.getUserId());
                throw new MyNotesAppException(EXCEPTION_FILE_FORMAT);
            }
        }
        if (isEdit) {
            user = userRepository.save(user);
            log.debug("Modified user with id: " + requestContext.getUserId());
        }
        return toDto(user);
    }

    private User findCurrentUser() {
        return userRepository.findById(requestContext.getUserId()).orElseThrow(() -> {
            log.warn("Not found user with id: " + requestContext.getUserId());
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
