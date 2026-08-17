package com.example.quality_management_service.management.mapper;

import com.example.quality_management_service.management.dto.RoleDto;
import com.example.quality_management_service.management.model.Permission;
import com.example.quality_management_service.management.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", implementationName = "ManagementRoleMapperImpl")
public interface RoleMapper {
    @Mapping(target = "permissionIds", source = "permissions", qualifiedByName = "mapPermissionIds")
    RoleDto toRoleDto(Role role);

    Role toRole(RoleDto dto);

    @Named("mapPermissionIds")
    default Set<Integer> mapPermissionIds(Set<Permission> permissions) {
        if (permissions == null) return null;
        return permissions.stream().map(Permission::getId).collect(Collectors.toSet());
    }
}
