package com.example.quality_management_service.management.config;

import com.example.quality_management_service.management.model.Role;
import com.example.quality_management_service.management.repository.RoleRepository;
import com.example.quality_management_service.management.repository.PermissionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RoleInitializer {

    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        return args -> {
            List<Role> defaultRoles = List.of(
                    new Role("SUPER_ADMIN", "Full system access"),
                    new Role("QA", "Quality Assurance role with limited permissions"),
                    new Role("QA_SUPERVISOR", "Supervisory role for QA team")
            );

            List<String> superAdminPermissionNames = List.of(
                "CREATE_USER",
                "GET_ALL_USERS",
                "GET_USER_BY_ID",
                "UPDATE_USER",
                "DELETE_USER"
            );

            for (Role role : defaultRoles) {
                if (!roleRepository.existsByRoleName(role.getRoleName())) {
                    if (role.getRoleName().equals("SUPER_ADMIN")) {
                        for (String permName : superAdminPermissionNames) {
                            permissionRepository.findByName(permName).ifPresent(role.getPermissions()::add);
                        }
                    }
                    roleRepository.save(role);
                } else if (role.getRoleName().equals("SUPER_ADMIN")) {
                    Role superAdmin = roleRepository.findByRoleName("SUPER_ADMIN").get();
                    for (String permName : superAdminPermissionNames) {
                        permissionRepository.findByName(permName).ifPresent(superAdmin.getPermissions()::add);
                    }
                    roleRepository.save(superAdmin);
                }
            }
        };
    }
}