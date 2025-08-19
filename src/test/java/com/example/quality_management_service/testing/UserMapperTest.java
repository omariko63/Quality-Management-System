package com.example.quality_management_service.testing;

import com.example.quality_management_service.management.dto.RoleDto;
import com.example.quality_management_service.management.dto.UserDto;
import com.example.quality_management_service.management.model.Role;
import com.example.quality_management_service.management.model.User;
import com.example.quality_management_service.management.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void testToDto() {
        Role role = new Role();
        role.setId(1);
        role.setRoleName("ADMIN");
        role.setDescription("Administrator role");
        role.setCreatedAt(LocalDateTime.now());
        User user = new User();
        user.setId(100);
        user.setUsername("testuser");
        user.setEmail("testuser@gmail.com");
        user.setPasswordHash("hashedpassword");
        user.setRole(role);
        UserDto dto = userMapper.toDto(user);
        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getUsername(), dto.getUsername());
        assertEquals(user.getEmail(), dto.getEmail());
        assertNotNull(dto.getRole());
        assertEquals(role.getId(), dto.getRole().getId());
        assertEquals(role.getRoleName(), dto.getRole().getRoleName());
    }

    @Test
    void testToEntity() {
        RoleDto roleDto = new RoleDto(2, "USER", "User role", LocalDateTime.now(), null);
        UserDto dto = new UserDto(200, "anotheruser", "anotheruser@gmail.com", "password123", roleDto);
        User user = userMapper.toEntity(dto);
        assertEquals(dto.getId(), user.getId());
        assertEquals(dto.getUsername(), user.getUsername());
        assertEquals(dto.getEmail(), user.getEmail());
        assertNotNull(user.getRole());
        assertEquals(roleDto.getId(), user.getRole().getId());
        assertEquals(roleDto.getRoleName(), user.getRole().getRoleName());
    }
}

