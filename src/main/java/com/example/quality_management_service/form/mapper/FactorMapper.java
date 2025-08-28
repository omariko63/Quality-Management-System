package com.example.quality_management_service.form.mapper;

import com.example.quality_management_service.form.dto.FactorDto;
import com.example.quality_management_service.form.model.Factor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FactorMapper {

    @Mapping(source = "category.id", target = "categoryId")
    FactorDto toDto(Factor factor);

    @Mapping(source = "categoryId", target = "category.id")
    Factor toEntity(FactorDto factorDTO);
}