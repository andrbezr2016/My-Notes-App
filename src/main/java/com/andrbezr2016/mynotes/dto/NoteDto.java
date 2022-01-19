package com.andrbezr2016.mynotes.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class NoteDto {

    private long id;

    private long categoryId;

    private String title;

    private String content;

    private OffsetDateTime deletedAt;

    private OffsetDateTime createdAt;

    private OffsetDateTime modifiedAt;
}
