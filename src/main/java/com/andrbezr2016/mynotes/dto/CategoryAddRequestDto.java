package com.andrbezr2016.mynotes.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.andrbezr2016.mynotes.constants.ExceptionConstants.*;

@Data
public class CategoryAddRequestDto {

    @NotNull(message = EXCEPTION_NOT_NULL)
    @Size(min = 1, max = 256, message = EXCEPTION_SIZE)
    private String title;
}
