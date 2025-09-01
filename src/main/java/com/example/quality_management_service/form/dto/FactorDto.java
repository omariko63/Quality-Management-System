package com.example.quality_management_service.form.dto;
import com.example.quality_management_service.form.enums.AnswerType;

import java.math.BigDecimal;

public record FactorDto(
        Long id,
        Long categoryId,
        String questionText,
        BigDecimal weight,
        AnswerType answerType,
        String notes,
        String passAnswer
){}
