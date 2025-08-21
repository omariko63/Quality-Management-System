package com.example.quality_management_service.management.config;

import com.example.quality_management_service.management.model.Permission;
import com.example.quality_management_service.management.repository.PermissionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PermissionInitializer {

    @Bean
    CommandLineRunner initPermissions(PermissionRepository permissionRepository) {
        return args -> {
            List<String> defaultPermissions = List.of(
                    "CREATE_USER",
                    "GET_ALL_USERS",
                    "GET_USER_BY_ID",
                    "UPDATE_USER",
                    "DELETE_USER"
            );

            for (String perm : defaultPermissions) {
                permissionRepository.findByName(perm).orElseGet(() -> {
                    Permission newPerm = new Permission(perm, "Permission for " + perm.toLowerCase().replace("_", " "));
                    return permissionRepository.save(newPerm);
                });
            }
        };
    }
}
