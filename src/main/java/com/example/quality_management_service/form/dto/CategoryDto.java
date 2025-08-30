package com.example.quality_management_service.form.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record CategoryDto(
    Long id,
    @NotNull Long formId,
    @NotNull @Size(max = 30) String title,
    @NotNull Double weight,
    @NotNull Long severityId,
    Integer orderIndex,
    List<FactorDto> factors
) {}
