package com.example.quality_management_service.form.controller;

import com.example.quality_management_service.form.dto.SeverityDto;
import com.example.quality_management_service.form.service.SeverityService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/severities")
public class SeverityController {

    private final SeverityService service;

    public SeverityController(SeverityService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('QA','QA_SUPERVISOR')")
    @GetMapping
    public List<SeverityDto> getAll() {
        return service.getAllSeverities();
    }
}
