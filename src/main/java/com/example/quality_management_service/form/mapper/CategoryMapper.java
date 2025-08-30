package com.example.quality_management_service.form.mapper;

import com.example.quality_management_service.form.dto.CategoryDto;
import com.example.quality_management_service.form.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
    CategoryDto toDto(Category category);
    Category toEntity(CategoryDto dto);
}

