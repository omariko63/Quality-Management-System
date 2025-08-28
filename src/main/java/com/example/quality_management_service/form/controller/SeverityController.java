package com.example.quality_management_service.form.controller;

import com.example.quality_management_service.form.model.Severity;
import com.example.quality_management_service.form.service.SeverityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/severities")
public class SeverityController {
    private final SeverityService severityService;

    public SeverityController(SeverityService severityService) {
        this.severityService = severityService;
    }

    @GetMapping
    public List<Severity> getAll() {
        return severityService.getAll();
    }

    @GetMapping("/{id}")
    public Severity getById(@PathVariable Long id) {
        return severityService.getById(id);
    }
}
