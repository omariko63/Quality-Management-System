package com.example.quality_management_service.mapper;

import com.example.quality_management_service.dto.RoleDto;
import com.example.quality_management_service.model.Permission;
import com.example.quality_management_service.model.Role;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleMapper {

    public Role toRole(RoleDto dto) {
        var role = new Role();
        role.setId(dto.id());
        role.setRoleName(dto.roleName());
        role.setDescription(dto.description());
        role.setCreatedAt(dto.createdAt());

        if (dto.permissionIds() != null) {
            Set<Permission> permissions = dto.permissionIds().stream()
                    .map(id -> {
                        Permission permission = new Permission();
                        permission.setId(id);
                        return permission;
                    })
                    .collect(Collectors.toSet());
            role.setPermissions(permissions);
        }

        return role;
    }

    public RoleDto toRoleDto(Role role) {
        Set<Integer> permissionIds = null;

        if (role.getPermissions() != null) {
            permissionIds = role.getPermissions().stream()
                    .map(Permission::getId)
                    .collect(Collectors.toSet());
        }

        return new RoleDto(
                role.getId(),
                role.getRoleName(),
                role.getDescription(),
                role.getCreatedAt(),
                permissionIds
        );
    }
}
