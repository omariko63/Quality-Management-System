package com.example.quality_management_service.management.config;

import com.example.quality_management_service.management.model.Role;
import com.example.quality_management_service.management.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RoleInitializer {

    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            List<Role> defaultRoles = List.of(
                    new Role("SUPER_ADMIN", "Full system access"),
                    new Role("QA", "Quality Assurance role with limited permissions"),
                    new Role("QA_SUPERVISOR", "Supervisory role for QA team")
            );

            for (Role role : defaultRoles) {
                if (!roleRepository.existsByRoleName(role.getRoleName())) {
                    roleRepository.save(role);
                }
            }
        };
    }
}