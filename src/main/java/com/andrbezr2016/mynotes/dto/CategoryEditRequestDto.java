package com.andrbezr2016.mynotes.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class CategoryEditRequestDto {

    @Size(min = 1, max = 256)
    private String title;
}
