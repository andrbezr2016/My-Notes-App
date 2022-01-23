package com.andrbezr2016.mynotes.controllers;

import com.andrbezr2016.mynotes.dto.CategoryDto;
import com.andrbezr2016.mynotes.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.andrbezr2016.mynotes.constants.ApiConstants.API_CATEGORIES_PATH;

@RequiredArgsConstructor
@RestController
@RequestMapping(API_CATEGORIES_PATH)
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getUserCategories() {
        return categoryService.getUserCategories();
    }

    @PostMapping
    public CategoryDto addCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.addCategory(categoryDto);
    }

    @PatchMapping("/{categoryId}")
    public CategoryDto editCategory(@PathVariable Long categoryId, @RequestBody CategoryDto categoryDto) {
        return categoryService.editCategory(categoryId, categoryDto);
    }

    @DeleteMapping("/{categoryId}")
    public CategoryDto deleteCategory(@PathVariable Long categoryId) {
        return categoryService.deleteCategory(categoryId);
    }
}
