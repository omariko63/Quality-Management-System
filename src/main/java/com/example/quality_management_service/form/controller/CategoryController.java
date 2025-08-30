package com.example.quality_management_service.form.controller;

import com.example.quality_management_service.form.dto.CategoryDto;
import com.example.quality_management_service.form.repository.CategoryRepository;
import com.example.quality_management_service.form.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;

    }
    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.createCategory(categoryDto));
    }
    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long id) {
        CategoryDto category = categoryService.findById(id);
        return ResponseEntity.ok(category);
    }
    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }
    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable Long id) {
        CategoryDto category = categoryService.findById(id);
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(category);
    }
    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory( @PathVariable Long id, @RequestBody CategoryDto categoryDto) {
       CategoryDto category = categoryService.updateCategory(id, categoryDto);
        return ResponseEntity.ok(category);
    }
}
