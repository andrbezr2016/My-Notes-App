package com.andrbezr2016.mynotes.services;

import com.andrbezr2016.mynotes.dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    public List<CategoryDto> getUserCategories() {
        return new ArrayList<>();
    }

    public CategoryDto addCategory(CategoryDto categoryDto) {
        return null;
    }

    public CategoryDto editCategory(Long categoryId, CategoryDto categoryDto) {
        return null;
    }

    public CategoryDto deleteCategory(Long categoryId) {
        return null;
    }
}
