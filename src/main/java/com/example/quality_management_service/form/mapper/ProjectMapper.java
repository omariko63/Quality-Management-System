package com.example.quality_management_service.form.mapper;

import com.example.quality_management_service.form.dto.ProjectDto;
import com.example.quality_management_service.form.model.EvaluationForm;
import com.example.quality_management_service.form.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    @Mapping(source = "evaluationForms", target = "evaluationFormIds", qualifiedByName = "mapEvaluationFormIds")
    ProjectDto toDTO(Project project);

    @Mapping(target = "evaluationForms", ignore = true)
    Project toEntity(ProjectDto projectDTO);

    @Named("mapEvaluationFormIds")
    default List<Long> mapEvaluationFormIds(List<EvaluationForm> evaluationForms) {
        if (evaluationForms == null) {
            return null;
        }
        return evaluationForms.stream()
                .map(EvaluationForm::getId)
                .collect(Collectors.toList());
    }
}
