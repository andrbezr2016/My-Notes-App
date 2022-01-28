package com.andrbezr2016.mynotes.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEditRequestDto {

    private String username;

    private String password;
}