package com.example.quality_management_service.form.mapper;

import com.example.quality_management_service.form.dto.EvaluationFormBulkCreateDto;
import com.example.quality_management_service.form.dto.EvaluationFormDTO;
import com.example.quality_management_service.form.dto.CategoryDto;
import com.example.quality_management_service.form.dto.SuccessCriteriaDto;
import com.example.quality_management_service.form.model.Category;
import com.example.quality_management_service.form.model.EvaluationForm;
import com.example.quality_management_service.form.model.Project;
import com.example.quality_management_service.form.model.SuccessCriteria;
import com.example.quality_management_service.management.model.User;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EvaluationFormMapper {

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "supervisor.id", target = "supervisorId")
    @Mapping(source = "categories", target = "categoryIds", qualifiedByName = "mapCategoryIds")
    @Mapping(source = "successCriteria", target = "successCriteriaIds", qualifiedByName = "mapSuccessCriteriaIds")
    EvaluationFormDTO toDTO(EvaluationForm form);

    @Mapping(target = "project", ignore = true)
    @Mapping(target = "supervisor", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "successCriteria", ignore = true)
    EvaluationForm toEntity(EvaluationFormDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(EvaluationFormDTO dto, @MappingTarget EvaluationForm entity);

    // Convert list of Category objects to their IDs
    @Named("mapCategoryIds")
    default List<Long> mapCategoryIds(List<Category> categories) {
        if (categories == null) return null;
        return categories.stream().map(Category::getId).collect(Collectors.toList());
    }

    // Convert list of SuccessCriteria objects to their IDs
    @Named("mapSuccessCriteriaIds")
    default List<Long> mapSuccessCriteriaIds(List<SuccessCriteria> criteria) {
        if (criteria == null) return null;
        return criteria.stream().map(SuccessCriteria::getId).collect(Collectors.toList());
    }

    // Bulk create mapping
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "supervisor", ignore = true)
    @Mapping(target = "categories", expression = "java(mapCategories(dto.categories()))")
    @Mapping(target = "successCriteria", expression = "java(mapSuccessCriteria(dto.successCriteria()))")
    EvaluationForm toEntity(EvaluationFormBulkCreateDto dto);

    default List<Category> mapCategories(List<CategoryDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(catDto -> {
            Category cat = new Category();
            cat.setTitle(catDto.title());
            cat.setWeight(catDto.weight());
            // Severity and form will be set in service
            return cat;
        }).collect(Collectors.toList());
    }

    default List<SuccessCriteria> mapSuccessCriteria(List<SuccessCriteriaDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(scDto -> {
            SuccessCriteria sc = new SuccessCriteria();
            sc.setThreshold(java.math.BigDecimal.valueOf(scDto.threshold()));
            // Severity and form will be set in service
            return sc;
        }).collect(Collectors.toList());
    }
}
