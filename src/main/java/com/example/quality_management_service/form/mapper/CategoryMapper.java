package com.example.quality_management_service.form.mapper;

import com.example.quality_management_service.form.dto.CategoryDto;
import com.example.quality_management_service.form.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryDto dto);
    CategoryDto toDto(Category category);
}
