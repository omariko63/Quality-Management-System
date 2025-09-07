package com.example.quality_management_service.form.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProjectDto(
    Long id,
    @NotBlank @Size(max = 30) String name,
    @Size(max = 255) String description,
    java.util.List<Long> evaluationFormIds
) {}
