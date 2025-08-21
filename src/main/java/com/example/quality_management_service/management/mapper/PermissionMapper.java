package com.example.quality_management_service.management.mapper;

import com.example.quality_management_service.management.dto.PermissionDTO;
import com.example.quality_management_service.management.model.Permission;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionDTO toDTO(Permission permission);
    Permission toEntity(PermissionDTO permissionDTO);
    List<PermissionDTO> toDTOList(List<Permission> permissions);
}
