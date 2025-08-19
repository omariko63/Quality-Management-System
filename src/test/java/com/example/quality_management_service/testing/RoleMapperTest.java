package com.example.quality_management_service.testing;

import com.example.quality_management_service.management.dto.RoleDto;
import com.example.quality_management_service.management.model.Permission;
import com.example.quality_management_service.management.model.Role;
import com.example.quality_management_service.management.mapper.RoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoleMapperTest {
    @Autowired
    private RoleMapper roleMapper;

    @Test
    void testToRoleDto() {
        Role role = new Role();
        role.setId(1);
        role.setRoleName("ADMIN");
        role.setDescription("Administrator role");
        role.setCreatedAt(LocalDateTime.now());
        Permission p1 = new Permission("READ", "Read permission");
        p1.setId(10);
        Permission p2 = new Permission("WRITE", "Write permission");
        p2.setId(20);
        Set<Permission> permissions = new HashSet<>(Arrays.asList(p1, p2));
        role.setPermissions(permissions);
        RoleDto dto = roleMapper.toRoleDto(role);
        assertEquals(role.getId(), dto.getId());
        assertEquals(role.getRoleName(), dto.getRoleName());
        assertEquals(role.getDescription(), dto.getDescription());
        assertEquals(role.getCreatedAt(), dto.getCreatedAt());
        assertTrue(dto.getPermissionIds().contains(10));
        assertTrue(dto.getPermissionIds().contains(20));
    }

    @Test
    void testToRole() {
        Set<Integer> permissionIds = new HashSet<>(Arrays.asList(10, 20));
        RoleDto dto = new RoleDto(2, "USER", "User role", LocalDateTime.now(), permissionIds);
        Role role = roleMapper.toRole(dto);
        assertEquals(dto.getId(), role.getId());
        assertEquals(dto.getRoleName(), role.getRoleName());
        assertEquals(dto.getDescription(), role.getDescription());
        assertEquals(dto.getCreatedAt(), role.getCreatedAt());
    }
}

