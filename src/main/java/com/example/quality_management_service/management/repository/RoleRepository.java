package com.example.quality_management_service.management.repository;

import com.example.quality_management_service.management.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    boolean existsByRoleName(String roleName);
}
