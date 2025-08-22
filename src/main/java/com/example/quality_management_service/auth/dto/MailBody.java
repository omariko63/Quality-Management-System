package com.example.quality_management_service.auth.dto;

import lombok.Builder;

@Builder
public record MailBody(
        String to,
        String subject,
        String text) {
}
