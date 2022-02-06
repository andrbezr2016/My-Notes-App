package com.andrbezr2016.mynotes.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CategoryAddRequestDto {

    @NotNull
    @Size(min = 1, max = 256)
    private String title;
}
