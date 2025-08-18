package com.example.quality_management_service.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record RoleDto(
        Integer id,
        String roleName,
        String description,
        LocalDateTime createdAt,
        Set<Integer> permissionIds
) {
    // Non-canonical constructor
    public RoleDto(Integer id, String roleName, String description) {
        this(id, roleName, description, LocalDateTime.now(), Set.of());

    }
}
