package com.andrbezr2016.mynotes.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationRequestDto {

    private String username;

    private String email;

    private String password;
}
