package com.example.quality_management_service.form.mapper;

import com.example.quality_management_service.form.dto.EvaluationFormDTO;
import com.example.quality_management_service.form.model.Category;
import com.example.quality_management_service.form.model.EvaluationForm;
import com.example.quality_management_service.form.model.SuccessCriteria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

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
}
