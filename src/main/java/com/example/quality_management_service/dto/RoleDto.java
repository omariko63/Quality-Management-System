package com.example.quality_management_service.dto;

import java.time.LocalDateTime;
import java.util.Set;

public class RoleDto {
    private Integer id;
    private String roleName;
    private String description;
    private LocalDateTime createdAt;
    private Set<Integer> permissionIds;

    public RoleDto() {}

    public RoleDto(Integer id, String roleName, String description) {
        this.id = id;
        this.roleName = roleName;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.permissionIds = Set.of();
    }

    public RoleDto(Integer id, String roleName, String description, LocalDateTime createdAt, Set<Integer> permissionIds) {
        this.id = id;
        this.roleName = roleName;
        this.description = description;
        this.createdAt = createdAt;
        this.permissionIds = permissionIds;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Set<Integer> getPermissionIds() { return permissionIds; }
    public void setPermissionIds(Set<Integer> permissionIds) { this.permissionIds = permissionIds; }
}
