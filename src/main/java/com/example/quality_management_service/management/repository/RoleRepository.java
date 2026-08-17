package com.example.quality_management_service.management.repository;

import com.example.quality_management_service.management.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface RoleRepository extends JpaRepository<Role, Integer> {
    boolean existsByRoleName(String roleName);
    Optional<Role> findByRoleName(String roleName);
}
