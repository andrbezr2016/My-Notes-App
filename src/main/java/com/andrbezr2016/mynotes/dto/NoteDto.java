package com.andrbezr2016.mynotes.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class NoteDto {

    private long id;

    private long categoryId;

    private String title;

    private String content;

    private boolean deletedFlag;

    private Date deletedAt;

    private Date createdAt;

    private Date modifiedAt;
}
