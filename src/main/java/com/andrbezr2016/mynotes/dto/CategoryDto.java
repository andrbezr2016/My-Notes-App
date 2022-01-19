package com.andrbezr2016.mynotes.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CategoryDto {

    private long id;

    private long userId;

    private String title;

    private Timestamp createdAt;

    private Timestamp modifiedAt;
}
