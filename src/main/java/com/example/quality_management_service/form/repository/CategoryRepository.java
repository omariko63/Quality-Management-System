package com.example.quality_management_service.form.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quality_management_service.form.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
