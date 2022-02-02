package com.andrbezr2016.mynotes.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class CategoryDto {

    private Long id;

    private String title;

    private OffsetDateTime createdAt;

    private OffsetDateTime modifiedAt;
}
