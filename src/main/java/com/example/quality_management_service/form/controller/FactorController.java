package com.example.quality_management_service.form.controller;

import com.example.quality_management_service.form.dto.FactorDto;
import com.example.quality_management_service.form.service.FactorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factors")
public class FactorController {

    public final FactorService factorService;

    public FactorController(FactorService factorService) {
        this.factorService = factorService;
    }

    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @PostMapping
    public ResponseEntity<FactorDto> createFactor(@RequestBody FactorDto factorDto) {
        FactorDto factor =  factorService.createFactor(factorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(factor);
    }

    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @GetMapping
    public ResponseEntity<List<FactorDto>> getAllFactors() {
        List<FactorDto>  factors= factorService.getAllFactors();
        return ResponseEntity.ok(factors);
    }

    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @GetMapping("/{id}")
    public ResponseEntity<FactorDto> getFactorById(@PathVariable Long id) {
        FactorDto factor = factorService.getFactorById(id);
        return ResponseEntity.ok(factor);
    }

    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @PutMapping("/{id}")
    public FactorDto updateFactor(@PathVariable Long id, @RequestBody FactorDto factor) {
        return factorService.updateFactor(id, factor);
    }

    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @DeleteMapping("/{id}")
    public void deleteFactor(@PathVariable Long id) {
        factorService.deleteFactor(id);
    }
}
