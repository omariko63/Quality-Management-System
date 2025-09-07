package com.example.quality_management_service.form.controller;

import com.example.quality_management_service.form.dto.EvaluationFormDTO;
import com.example.quality_management_service.form.dto.FullFormCreateRequest;
import com.example.quality_management_service.form.service.EvaluationFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluation-forms")
@RequiredArgsConstructor
public class EvaluationFormController {

    private final EvaluationFormService evaluationFormService;

    // CREATE
    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @PostMapping
    public ResponseEntity<EvaluationFormDTO> create(@RequestBody EvaluationFormDTO dto) {
        EvaluationFormDTO created = evaluationFormService.createEvaluationForm(dto);
        return ResponseEntity.ok(created);
    }

    // CREATE EVERYTHING IN ONE REQUEST
    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @PostMapping("/full-create")
    public ResponseEntity<?> createFullForm(@RequestBody FullFormCreateRequest request) {
        return ResponseEntity.ok(evaluationFormService.createFullForm(request));
    }

    // READ - by ID
    @PreAuthorize("hasAnyRole('QA','QA_SUPERVISOR')")
    @GetMapping("/{id}")
    public ResponseEntity<EvaluationFormDTO> getById(@PathVariable Long id) {
        return evaluationFormService.getEvaluationForm(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // READ - all
    @PreAuthorize("hasAnyRole('QA','QA_SUPERVISOR')")
    @GetMapping
    public ResponseEntity<List<EvaluationFormDTO>> getAll() {
        return ResponseEntity.ok(evaluationFormService.getAllEvaluationForms());
    }

    // UPDATE
    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @PutMapping("/{id}")
    public ResponseEntity<EvaluationFormDTO> update(@PathVariable Long id,
                                                    @RequestBody EvaluationFormDTO dto) {
        return evaluationFormService.updateEvaluationForm(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = evaluationFormService.deleteEvaluationForm(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
