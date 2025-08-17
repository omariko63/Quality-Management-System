package com.example.quality_management_service.repository;

import com.example.quality_management_service.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    boolean existsByRoleName(String roleName);
}
