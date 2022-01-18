package com.andrbezr2016.mynotes.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class CategoryDto {

    private long id;

    private long userId;

    private String title;

    private Date createdAt;

    private Date modifiedAt;
}
