package com.example.quality_management_service.service;

import com.example.quality_management_service.dto.RoleDto;
import com.example.quality_management_service.mapper.RoleMapper;
import com.example.quality_management_service.model.Permission;
import com.example.quality_management_service.model.Role;
import com.example.quality_management_service.repository.PermissionRepository;
import com.example.quality_management_service.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;

    public RoleService(RoleRepository roleRepository,
                       PermissionRepository permissionRepository,
                       RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.roleMapper = roleMapper;
    }

    public Map<String, Object> create(RoleDto dto) {
        try {
            if (roleRepository.existsByRoleName(dto.getRoleName())) {
                throw new IllegalStateException("Role already exists");
            }

            Role role = roleMapper.toRole(dto);
            if (role.getCreatedAt() == null) {
                role.setCreatedAt(LocalDateTime.now());
            }

            Set<Permission> permissions = loadPermissions(dto.getPermissionIds());
            role.setPermissions(permissions);

            Role saved = roleRepository.save(role);

            return Map.of(
                    "success", true,
                    "message", "Role created successfully",
                    "data", roleMapper.toRoleDto(saved)
            );
        } catch (IllegalStateException e) {
            return Map.of("success", false, "message", e.getMessage());
        } catch (Exception e) {
            return Map.of("success", false, "message", "Unexpected error: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Map<String, Object> findAll() {
        try {
            List<RoleDto> roles = roleRepository.findAll().stream()
                    .map(roleMapper::toRoleDto)
                    .toList();

            return Map.of(
                    "success", true,
                    "message", "Roles fetched successfully",
                    "count", roles.size(),
                    "data", roles
            );
        } catch (Exception e) {
            return Map.of("success", false, "message", "Unexpected error: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Map<String, Object> findById(Integer id) {
        try {
            Role role = roleRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Role not found"));

            return Map.of(
                    "success", true,
                    "message", "Role fetched successfully",
                    "data", roleMapper.toRoleDto(role)
            );
        } catch (NoSuchElementException e) {
            return Map.of("success", false, "message", e.getMessage());
        } catch (Exception e) {
            return Map.of("success", false, "message", "Unexpected error: " + e.getMessage());
        }
    }

    public Map<String, Object> update(Integer id, RoleDto dto) {
        try {
            Role role = roleRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Role not found"));

            if (dto.getRoleName() != null) {
                role.setRoleName(dto.getRoleName());
            }
            if (dto.getDescription() != null) {
                role.setDescription(dto.getDescription());
            }
            if (dto.getPermissionIds()!= null) {
                Set<Permission> permissions = loadPermissions(dto.getPermissionIds());
                role.setPermissions(permissions);
            }

            Role updated = roleRepository.save(role);

            return Map.of(
                    "success", true,
                    "message", "Role updated successfully",
                    "data", roleMapper.toRoleDto(updated)
            );
        } catch (NoSuchElementException e) {
            return Map.of("success", false, "message", e.getMessage());
        } catch (Exception e) {
            return Map.of("success", false, "message", "Unexpected error: " + e.getMessage());
        }
    }

    public Map<String, Object> delete(Integer id) {
        try {
            if (!roleRepository.existsById(id)) {
                throw new NoSuchElementException("Role not found");
            }

            roleRepository.deleteById(id);

            return Map.of(
                    "success", true,
                    "message", "Role deleted successfully"
            );
        } catch (NoSuchElementException e) {
            return Map.of("success", false, "message", e.getMessage());
        } catch (Exception e) {
            return Map.of("success", false, "message", "Unexpected error: " + e.getMessage());
        }
    }

    private Set<Permission> loadPermissions(Set<Integer> ids) {
        if (ids == null || ids.isEmpty()) return new HashSet<>();
        List<Permission> found = permissionRepository.findAllById(ids);
        Set<Integer> foundIds = found.stream().map(Permission::getId).collect(Collectors.toSet());

        Set<Integer> missing = ids.stream().filter(i -> !foundIds.contains(i)).collect(Collectors.toSet());
        if (!missing.isEmpty()) {
            throw new NoSuchElementException("Permissions not found: " + missing);
        }

        return new HashSet<>(found);
    }
}
