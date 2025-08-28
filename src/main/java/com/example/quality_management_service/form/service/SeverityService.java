package com.example.quality_management_service.form.service;

import com.example.quality_management_service.form.dto.SeverityDto;
import com.example.quality_management_service.form.mapper.SeverityMapper;
import com.example.quality_management_service.form.repository.SeverityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeverityService {

    private final SeverityRepository repository;
    private final SeverityMapper mapper;

    public SeverityService(SeverityRepository repository, SeverityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<SeverityDto> getAllSeverities() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}
