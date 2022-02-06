package com.andrbezr2016.mynotes.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class NoteAddRequestDto {

    @Positive
    private Long categoryId;

    @NotNull
    @Size(min = 1, max = 256)
    private String title;

    private String content;
}
