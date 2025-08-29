package com.example.quality_management_service.form.controller;

import com.example.quality_management_service.form.dto.FactorDto;
import com.example.quality_management_service.form.model.Factor;
import com.example.quality_management_service.form.service.FactorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factors")
public class FactorController {

    public final FactorService factorService;

    public FactorController(FactorService factorService) {
        this.factorService = factorService;
    }

    @PostMapping
    public ResponseEntity<FactorDto> createFactor(@RequestBody FactorDto factorDto) {
        FactorDto factor =  factorService.createFactor(factorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(factor);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<FactorDto>> getAllFactors() {
        List<FactorDto>  factors= factorService.getAllFactors();
        return ResponseEntity.ok(factors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FactorDto> getFactorById(@PathVariable Long id) {
        FactorDto factor = factorService.getFactorById(id);
        return ResponseEntity.ok(factor);
    }

    // UPDATE
    @PutMapping("/{id}")
    public FactorDto updateFactor(@PathVariable Long id, @RequestBody FactorDto factor) {
        return factorService.updateFactor(id, factor);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteFactor(@PathVariable Long id) {
        factorService.deleteFactor(id);

    }
}