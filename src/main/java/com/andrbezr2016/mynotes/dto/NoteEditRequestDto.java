package com.andrbezr2016.mynotes.dto;

import lombok.Data;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class NoteEditRequestDto {

    @Positive
    private Long categoryId;

    @Size(min = 1, max = 256)
    private String title;

    private String content;
}
