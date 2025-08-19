package com.example.quality_management_service.management.mapper;

import com.example.quality_management_service.management.dto.UserDto;
import com.example.quality_management_service.management.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password", ignore = true)
    UserDto toDto(User user);

    @Mapping(target = "passwordHash", source = "password")
    User toEntity(UserDto dto);
}
