package com.andrbezr2016.mynotes.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class CategoryDto {

    private long id;

    private String title;

    private OffsetDateTime createdAt;

    private OffsetDateTime modifiedAt;
}
