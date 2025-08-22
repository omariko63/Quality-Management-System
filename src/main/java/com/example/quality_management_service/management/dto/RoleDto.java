package com.example.quality_management_service.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private Integer id;
    private String roleName;
    private String description;
    private LocalDateTime createdAt;
    private Set<Integer> permissionIds;

    public RoleDto(Integer id, String roleName, String description) {
        this.id = id;
        this.roleName = roleName;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.permissionIds = Set.of();
    }
}
