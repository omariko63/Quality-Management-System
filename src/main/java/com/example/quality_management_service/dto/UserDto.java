package com.example.quality_management_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDto(
        Integer id,

        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Email is required")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@gmail\\.com$", message = "Email must be a valid Gmail address")
        String email,

        @Size(min = 6, message = "Password must be at least 6 characters")
        String password,

        RoleDto role
) {}
