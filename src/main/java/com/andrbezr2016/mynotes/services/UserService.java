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
import org.imgscalr.Scalr;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

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
                Path path = saveIcon(icon);
                if (user.getIconPath() == null) user.setIconPath(path.toString());
                isEdit = true;
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
        String icon = loadIcon(user);
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .icon(icon)
                .createdAt(user.getCreatedAt())
                .modifiedAt(user.getModifiedAt())
                .build();
    }

    private Path saveIcon(MultipartFile icon) {
        try {
            Files.createDirectories(Paths.get(properties.getStorageLocation()));
            Path path = Paths.get(properties.getStorageLocation(), requestContext.getUserId() + ".jpg");
            BufferedImage originalImage = ImageIO.read(icon.getInputStream());
            int minSize = Math.min(originalImage.getWidth(), originalImage.getHeight());
            BufferedImage croppedImage = originalImage.getSubimage(0, 0, minSize, minSize);
            BufferedImage resizedImage = Scalr.resize(croppedImage, 256);
            ImageIO.write(resizedImage, "jpg", path.toFile());
            return path;
        } catch (IOException e) {
            throw new StorageException(e);
        }
    }

    private String loadIcon(User user) {
        String icon = null;
        if (user.getIconPath() != null) {
            try {
                byte[] iconBytes = Files.readAllBytes(Paths.get(user.getIconPath()));
                icon = Base64.getEncoder().encodeToString(iconBytes);
            } catch (IOException e) {
                log.warn("Icon not found for user with id: " + requestContext.getUserId());
            }
        }
        return icon;
    }
}
