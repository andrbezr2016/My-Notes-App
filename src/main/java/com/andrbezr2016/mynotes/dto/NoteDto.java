package com.andrbezr2016.mynotes.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class NoteDto {

    private long id;

    private long categoryId;

    private String title;

    private String content;

    private boolean deletedFlag;

    private Timestamp deletedAt;

    private Timestamp createdAt;

    private Timestamp modifiedAt;
}
