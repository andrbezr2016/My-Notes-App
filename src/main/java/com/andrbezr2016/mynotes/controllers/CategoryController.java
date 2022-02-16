package com.andrbezr2016.mynotes.controllers;

import com.andrbezr2016.mynotes.dto.CategoryAddRequestDto;
import com.andrbezr2016.mynotes.dto.CategoryDto;
import com.andrbezr2016.mynotes.dto.CategoryEditRequestDto;
import com.andrbezr2016.mynotes.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

import static com.andrbezr2016.mynotes.constants.ApiConstants.API_CATEGORIES_PATH;
import static com.andrbezr2016.mynotes.constants.ExceptionConstants.*;

@Validated
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
    public CategoryDto addCategory(@RequestBody @Valid CategoryAddRequestDto categoryAddRequestDto) {
        return categoryService.addCategory(categoryAddRequestDto);
    }

    @PatchMapping("/{categoryId}")
    public CategoryDto editCategory(@PathVariable @Positive(message = EXCEPTION_POSITIVE) Long categoryId, @RequestBody @Valid CategoryEditRequestDto categoryEditRequestDto) {
        return categoryService.editCategory(categoryId, categoryEditRequestDto);
    }

    @DeleteMapping("/{categoryId}")
    public CategoryDto deleteCategory(@PathVariable @Positive(message = EXCEPTION_POSITIVE) Long categoryId) {
        return categoryService.deleteCategory(categoryId);
    }
}
