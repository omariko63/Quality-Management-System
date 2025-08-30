package com.example.quality_management_service.form.service;

import com.example.quality_management_service.form.dto.CategoryDto;
import com.example.quality_management_service.form.mapper.CategoryMapper;
import com.example.quality_management_service.form.model.Category;
import com.example.quality_management_service.form.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(categoryMapper::toDto).collect(Collectors.toList());
    }
    public CategoryDto findById(Long id) {
        return categoryRepository.findById(id).map(categoryMapper::toDto).orElse(null);
    }
    /*
    public CategoryDto findByName(String name) {
    }*/
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
    public CategoryDto createCategory(CategoryDto categoryDto) {
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(categoryDto)));
    }
    public CategoryDto updateCategory(Long categoryId, CategoryDto updatedData) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        if (updatedData.title() != null) {
            category.setTitle(updatedData.title());
        }

        if (updatedData.weight() != null) {
            category.setWeight(updatedData.weight());
        }

        if (updatedData.orderIndex() != null) {
            category.setOrderIndex(updatedData.orderIndex());
        }

        categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }
}
