package com.andrbezr2016.mynotes.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class UserDto {

    private long id;

    private String username;

    private String email;

    private String iconPath;

    private OffsetDateTime createdAt;

    private OffsetDateTime modifiedAt;
}
