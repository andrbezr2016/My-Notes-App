package com.andrbezr2016.mynotes.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class UserDto {

    private Long id;

    private String username;

    private String email;

    private String icon;

    private OffsetDateTime createdAt;

    private OffsetDateTime modifiedAt;
}
