package com.example.quality_management_service.service;

import com.example.quality_management_service.dto.PermissionDTO;
import com.example.quality_management_service.mapper.PermissionMapper;
import com.example.quality_management_service.model.Permission;
import com.example.quality_management_service.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    public PermissionService(PermissionRepository permissionRepository, PermissionMapper permissionMapper) {
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;
    }

    public List<PermissionDTO> getAllPermissions() {
        return permissionMapper.toDTOList(permissionRepository.findAll());
    }

    public PermissionDTO getPermissionById(Integer id) {
        Optional<Permission> permission = permissionRepository.findById(id);
        return permission.map(permissionMapper::toDTO).orElse(null);
    }

    public PermissionDTO createPermission(PermissionDTO dto) {
        Permission permission = permissionMapper.toEntity(dto);
        return permissionMapper.toDTO(permissionRepository.save(permission));
    }

    public PermissionDTO updatePermission(Integer id, PermissionDTO dto) {
        return permissionRepository.findById(id)
                .map(existing -> {
                    existing.setName(dto.getName());
                    existing.setDescription(dto.getDescription());
                    return permissionMapper.toDTO(permissionRepository.save(existing));
                }).orElse(null);
    }

    public boolean deletePermission(Integer id) {
        if (permissionRepository.existsById(id)) {
            permissionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
