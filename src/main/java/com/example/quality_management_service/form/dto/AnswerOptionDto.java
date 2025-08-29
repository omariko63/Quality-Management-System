package com.example.quality_management_service.form.dto;

import java.math.BigDecimal;

public record AnswerOptionDto(
        Long id,
        Long factorId,
        Integer value,
        String label,
        Boolean isPassing,
        BigDecimal weight
) {}
