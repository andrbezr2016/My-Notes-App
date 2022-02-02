package com.andrbezr2016.mynotes.services;

import com.andrbezr2016.mynotes.contexts.RequestContext;
import com.andrbezr2016.mynotes.dto.CategoryDto;
import com.andrbezr2016.mynotes.entities.Category;
import com.andrbezr2016.mynotes.exceptions.MyNotesAppException;
import com.andrbezr2016.mynotes.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
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

    public CategoryDto addCategory(CategoryDto categoryDto) {
        OffsetDateTime currentTime = OffsetDateTime.now();
        Category category = categoryRepository.save(Category.builder()
                .userId(requestContext.getUserId())
                .title(categoryDto.getTitle())
                .createdAt(currentTime)
                .modifiedAt(currentTime)
                .build());
        log.info("Added category with id: " + category.getId());
        return toDto(category);
    }

    public CategoryDto editCategory(Long categoryId, CategoryDto categoryDto) {
        Category category = findCategory(categoryId);
        boolean isEdit = false;
        if (categoryDto.getTitle() != null) {
            category.setTitle(categoryDto.getTitle());
            isEdit = true;
        }
        if (isEdit) {
            category.setModifiedAt(OffsetDateTime.now());
            category = categoryRepository.save(category);
            log.info("Modified category with id: " + categoryId);
        }
        return toDto(category);
    }

    public CategoryDto deleteCategory(Long categoryId) {
        Category category = findCategory(categoryId);
        categoryRepository.delete(category);
        log.info("Deleted category with id: " + categoryId);
        return toDto(category);
    }

    private Category findCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> {
            log.info("Not found category with id: " + categoryId);
            return new MyNotesAppException(EXCEPTION_CATEGORY_NOT_FOUND);
        });
        if (category.getUserId().equals(requestContext.getUserId())) {
            return category;
        } else {
            log.info("User with id: " + requestContext.getUserId() + " no access to category with id: " + categoryId);
            throw new MyNotesAppException(EXCEPTION_NO_ACCESS_TO_CATEGORY);
        }
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
