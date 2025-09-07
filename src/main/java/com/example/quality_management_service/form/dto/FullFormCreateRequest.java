package com.example.quality_management_service.form.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import com.example.quality_management_service.form.dto.ProjectDto;
import com.example.quality_management_service.form.dto.SeverityDto;
import com.example.quality_management_service.form.dto.EvaluationFormBulkCreateDto;

public class FullFormCreateRequest {
    @NotNull
    public ProjectDto project;
    public List<SeverityDto> severities;
    @NotNull
    public EvaluationFormBulkCreateDto form;
}
