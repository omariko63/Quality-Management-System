package com.example.quality_management_service.service;

import com.example.quality_management_service.dto.RoleDto;
import com.example.quality_management_service.mapper.RoleMapper;
import com.example.quality_management_service.model.Permission;
import com.example.quality_management_service.model.Role;
import com.example.quality_management_service.repository.PermissionRepository;
import com.example.quality_management_service.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public RoleDto create(RoleDto dto) {
        if (roleRepository.existsByRoleName(dto.roleName())) {
            throw new IllegalStateException("Role already exists");
        }

        Set<Permission> permissions = loadPermissions(dto.permissionIds());
        Role role = roleMapper.toRole(dto);
        role.setPermissions(permissions);

        return roleMapper.toRoleDto(roleRepository.save(role));
    }

    @Transactional(readOnly = true)
    public List<RoleDto> findAll() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toRoleDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public RoleDto findById(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Role not found"));
        return roleMapper.toRoleDto(role);
    }

    public RoleDto update(Integer id, RoleDto dto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Role not found"));

        if (dto.roleName() != null) {
            role.setRoleName(dto.roleName());
        }
        if (dto.description() != null) {
            role.setDescription(dto.description());
        }
        if (dto.permissionIds() != null) {
            Set<Permission> permissions = loadPermissions(dto.permissionIds());
            role.setPermissions(permissions);
        }

        return roleMapper.toRoleDto(roleRepository.save(role));
    }

    public void delete(Integer id) {
        if (!roleRepository.existsById(id)) {
            throw new NoSuchElementException("Role not found");
        }
        roleRepository.deleteById(id);
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
