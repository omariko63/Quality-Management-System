package com.example.quality_management_service.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@gmail\\.com$", message = "Email must be a valid Gmail address")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private RoleDto role;
}
