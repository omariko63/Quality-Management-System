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
            List<String> qaSupervisorPermissionNames = List.of(
                "CREATE_USER",
                "GET_ALL_USERS",
                "GET_USER_BY_ID",
                "UPDATE_USER",
                "DELETE_USER"
            );
            List<String> qaPermissionNames = List.of(
                "GET_ALL_USERS",
                "GET_USER_BY_ID"
            );

            // Ensure permissions exist
            for (String permName : superAdminPermissionNames) {
                if (!permissionRepository.findByName(permName).isPresent()) {
                    permissionRepository.save(new com.example.quality_management_service.management.model.Permission(permName, permName + " permission"));
                }
            }
            for (String permName : qaSupervisorPermissionNames) {
                if (!permissionRepository.findByName(permName).isPresent()) {
                    permissionRepository.save(new com.example.quality_management_service.management.model.Permission(permName, permName + " permission"));
                }
            }
            for (String permName : qaPermissionNames) {
                if (!permissionRepository.findByName(permName).isPresent()) {
                    permissionRepository.save(new com.example.quality_management_service.management.model.Permission(permName, permName + " permission"));
                }
            }

            for (Role role : defaultRoles) {
                Role savedRole = roleRepository.findByRoleName(role.getRoleName()).orElseGet(() -> roleRepository.save(role));
                if (role.getRoleName().equals("SUPER_ADMIN")) {
                    for (String permName : superAdminPermissionNames) {
                        permissionRepository.findByName(permName).ifPresent(savedRole.getPermissions()::add);
                    }
                    roleRepository.save(savedRole);
                } else if (role.getRoleName().equals("QA_SUPERVISOR")) {
                    for (String permName : qaSupervisorPermissionNames) {
                        permissionRepository.findByName(permName).ifPresent(savedRole.getPermissions()::add);
                    }
                    roleRepository.save(savedRole);
                } else if (role.getRoleName().equals("QA")) {
                    for (String permName : qaPermissionNames) {
                        permissionRepository.findByName(permName).ifPresent(savedRole.getPermissions()::add);
                    }
                    roleRepository.save(savedRole);
                }
            }
        };
    }
}
