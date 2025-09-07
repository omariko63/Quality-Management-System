package com.example.quality_management_service.form.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record CategoryDto(
    Long id,
    Long formId, // removed @NotNull to allow optional for nested creation
    @NotNull @Size(max = 30) String title,
    @NotNull BigDecimal weight,
    @NotNull Long severityId,
    List<FactorDto> factors,
    List<AnswerOptionDto> options
) {}
