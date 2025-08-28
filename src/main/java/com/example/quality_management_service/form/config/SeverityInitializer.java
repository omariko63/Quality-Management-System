package com.example.quality_management_service.form.config;

import com.example.quality_management_service.form.model.Severity;
import com.example.quality_management_service.form.repository.SeverityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeverityInitializer {

    @Bean
    public CommandLineRunner initializeSeverities(SeverityRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Severity(1L, "Low", "Low severity level", 0.2));
                repository.save(new Severity(2L, "Medium", "Medium severity level", 0.5));
                repository.save(new Severity(3L, "High", "High severity level", 0.8));
            }
        };
    }
}
