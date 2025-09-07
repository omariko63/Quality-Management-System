package com.example.quality_management_service.form.dto;

import com.example.quality_management_service.form.enums.CalculationMethod;
import com.example.quality_management_service.form.enums.FormStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.List;

public record EvaluationFormBulkCreateDto(
    Long id,
    Long projectId,
    @NotBlank @Size(max = 30) String nameEn,
    @NotBlank @Size(max = 30) String nameAr,
    CalculationMethod calculationMethod,
    FormStatus status,
    Instant createdAt,
    Instant updatedAt,
    Integer supervisorId,
    List<CategoryDto> categories,
    List<SuccessCriteriaDto> successCriteria
) {}

