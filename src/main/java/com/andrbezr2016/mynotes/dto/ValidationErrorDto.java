package com.andrbezr2016.mynotes.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationErrorDto {

    private String field;
    private String message;
}
