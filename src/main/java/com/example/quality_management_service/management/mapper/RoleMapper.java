package com.example.quality_management_service.management.mapper;

import com.example.quality_management_service.management.dto.RoleDto;
import com.example.quality_management_service.management.model.Permission;
import com.example.quality_management_service.management.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(target = "permissionIds", source = "permissions", qualifiedByName = "mapPermissionIds")
    RoleDto toRoleDto(Role role);

    Role toRole(RoleDto dto);

    @Named("mapPermissionIds")
    default Set<Integer> mapPermissionIds(Set<Permission> permissions) {
        if (permissions == null) return null;
        return permissions.stream().map(Permission::getId).collect(Collectors.toSet());
    }
}
