package com.example.quality_management_service.mapper;

import com.example.quality_management_service.dto.RoleDto;
import com.example.quality_management_service.dto.UserDto;
import com.example.quality_management_service.model.Role;
import com.example.quality_management_service.model.User;

public class UserMapper {

    public static UserDto toDto(User user) {
        RoleDto roleDto = null;
        if (user.getRole() != null) {
            roleDto = new RoleDto(
                user.getRole().getId(),
                user.getRole().getRoleName(),
                user.getRole().getDescription()
            );
        }
        return new UserDto(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            null,
            roleDto
        );
    }

    public static User toEntity(UserDto dto, Role role, String hashedPassword) {
        User user = new User();
        user.setId(dto.id());
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPasswordHash(hashedPassword);
        user.setRole(role);
        return user;
    }
}
