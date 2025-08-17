package com.example.quality_management_service.mapper;

import com.example.quality_management_service.dto.PermissionDTO;
import com.example.quality_management_service.model.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionMapper INSTANCE = Mappers.getMapper(PermissionMapper.class);

    PermissionDTO toDTO(Permission permission);
    Permission toEntity(PermissionDTO permissionDTO);
    List<PermissionDTO> toDTOList(List<Permission> permissions);
}
