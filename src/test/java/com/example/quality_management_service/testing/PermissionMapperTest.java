package com.example.quality_management_service.testing;

import com.example.quality_management_service.dto.PermissionDTO;
import com.example.quality_management_service.model.Permission;
import com.example.quality_management_service.mapper.PermissionMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PermissionMapperTest {

    @Autowired
    private PermissionMapper permissionMapper;

    @Test
    void testToDTO() {
        Permission permission = new Permission("READ", "Read permission");
        permission.setId(1);
        PermissionDTO dto = permissionMapper.toDTO(permission);
        assertEquals(permission.getId(), dto.getId());
        assertEquals(permission.getName(), dto.getName());
        assertEquals(permission.getDescription(), dto.getDescription());
    }

    @Test
    void testToEntity() {
        PermissionDTO dto = new PermissionDTO(2, "WRITE", "Write permission");
        Permission permission = permissionMapper.toEntity(dto);
        assertEquals(dto.getId(), permission.getId());
        assertEquals(dto.getName(), permission.getName());
        assertEquals(dto.getDescription(), permission.getDescription());
    }

    @Test
    void testToDTOList() {
        Permission p1 = new Permission("READ", "Read permission");
        p1.setId(1);
        Permission p2 = new Permission("WRITE", "Write permission");
        p2.setId(2);
        List<Permission> permissions = Arrays.asList(p1, p2);
        List<PermissionDTO> dtos = permissionMapper.toDTOList(permissions);
        assertEquals(2, dtos.size());
        assertEquals(p1.getId(), dtos.get(0).getId());
        assertEquals(p2.getId(), dtos.get(1).getId());
    }
}

