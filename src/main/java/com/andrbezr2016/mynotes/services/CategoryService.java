package com.andrbezr2016.mynotes.services;

import com.andrbezr2016.mynotes.contexts.RequestContext;
import com.andrbezr2016.mynotes.dto.CategoryAddRequestDto;
import com.andrbezr2016.mynotes.dto.CategoryDto;
import com.andrbezr2016.mynotes.dto.CategoryEditRequestDto;
import com.andrbezr2016.mynotes.entities.Category;
import com.andrbezr2016.mynotes.exceptions.MyNotesAppException;
import com.andrbezr2016.mynotes.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.andrbezr2016.mynotes.constants.ExceptionConstants.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final RequestContext requestContext;

    public List<CategoryDto> getUserCategories() {
        List<Category> categoryList = categoryRepository.findAllByUserId(requestContext.getUserId());
        return categoryList.stream().map(category -> CategoryDto.builder()
                .id(category.getId())
                .title(category.getTitle())
                .createdAt(category.getCreatedAt())
                .modifiedAt(category.getModifiedAt())
                .build()).collect(Collectors.toList());
    }

    public CategoryDto addCategory(CategoryAddRequestDto categoryAddRequestDto) {
        Category category = categoryRepository.save(Category.builder()
                .userId(requestContext.getUserId())
                .title(categoryAddRequestDto.getTitle())
                .build());
        log.debug("Added category with id: " + category.getId());
        return toDto(category);
    }

    public CategoryDto editCategory(Long categoryId, CategoryEditRequestDto categoryEditRequestDto) {
        Category category = findCategory(categoryId);
        boolean isEdit = false;
        if (categoryEditRequestDto.getTitle() != null && !categoryEditRequestDto.getTitle().equals(category.getTitle())) {
            category.setTitle(categoryEditRequestDto.getTitle());
            isEdit = true;
        }
        if (isEdit) {
            category = categoryRepository.save(category);
            log.debug("Modified category with id: " + categoryId);
        }
        return toDto(category);
    }

    public CategoryDto deleteCategory(Long categoryId) {
        Category category = findCategory(categoryId);
        categoryRepository.delete(category);
        log.debug("Deleted category with id: " + categoryId);
        return toDto(category);
    }

    private Category findCategory(Long categoryId) {
        return categoryRepository.findByIdAndUserId(categoryId, requestContext.getUserId()).orElseThrow(() -> {
            log.warn("User with id: " + requestContext.getUserId() + " has no category with id: " + categoryId);
            return new MyNotesAppException(EXCEPTION_CATEGORY_NOT_FOUND);
        });
    }

    private CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .title(category.getTitle())
                .createdAt(category.getCreatedAt())
                .modifiedAt(category.getModifiedAt())
                .build();
    }
}
