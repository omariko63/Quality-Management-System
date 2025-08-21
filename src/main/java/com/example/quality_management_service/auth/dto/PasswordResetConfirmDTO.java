package com.example.quality_management_service.auth.dto;

public record PasswordResetConfirmDTO(String token, String newPassword) {}
