package com.example.quality_management_service.form.repository;

import com.example.quality_management_service.form.model.Severity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeverityRepository extends JpaRepository<Severity, Long> {
}

