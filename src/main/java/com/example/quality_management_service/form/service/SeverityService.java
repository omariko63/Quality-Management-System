package com.example.quality_management_service.form.service;

import com.example.quality_management_service.form.model.Severity;
import com.example.quality_management_service.form.repository.SeverityRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SeverityService {
    private final SeverityRepository severityRepository;

    public SeverityService(SeverityRepository severityRepository) {
        this.severityRepository = severityRepository;
    }

    public List<Severity> getAll() {
        return severityRepository.findAll();
    }

    public Severity getById(Long id) {
        return severityRepository.findById(id).orElseThrow();
    }
}

