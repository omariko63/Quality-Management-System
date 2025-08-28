package com.example.quality_management_service.form.dto;

import java.math.BigDecimal;

public record SeverityDto(
        Long id,
        String name,
        String description,
        BigDecimal threshold
) {}

