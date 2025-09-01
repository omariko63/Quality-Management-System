package com.example.quality_management_service.form.mapper;

import com.example.quality_management_service.form.dto.AnswerOptionDto;
import com.example.quality_management_service.form.model.AnswerOption;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AnswerOptionMapper {

    @Mapping(source = "factor.id", target = "factorId")
    @Mapping(source = "passing", target = "isPassing")
    AnswerOptionDto toDto(AnswerOption entity);

    @Mapping(source = "factorId", target = "factor.id")
    @Mapping(source = "isPassing", target = "passing")
    AnswerOption toEntity(AnswerOptionDto dto);
}
