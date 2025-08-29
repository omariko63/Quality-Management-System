package com.example.quality_management_service.form.controller;

import com.example.quality_management_service.form.dto.EvaluationFormDTO;
import com.example.quality_management_service.form.service.EvaluationFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluation-forms")
@RequiredArgsConstructor
public class EvaluationFormController {

    private final EvaluationFormService evaluationFormService;

    // CREATE
    @PostMapping
    public ResponseEntity<EvaluationFormDTO> create(@RequestBody EvaluationFormDTO dto) {
        EvaluationFormDTO created = evaluationFormService.createEvaluationForm(dto);
        return ResponseEntity.ok(created);
    }

    // READ - by ID
    @GetMapping("/{id}")
    public ResponseEntity<EvaluationFormDTO> getById(@PathVariable Long id) {
        return evaluationFormService.getEvaluationForm(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // READ - all
    @GetMapping
    public ResponseEntity<List<EvaluationFormDTO>> getAll() {
        return ResponseEntity.ok(evaluationFormService.getAllEvaluationForms());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<EvaluationFormDTO> update(@PathVariable Long id,
                                                    @RequestBody EvaluationFormDTO dto) {
        return evaluationFormService.updateEvaluationForm(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = evaluationFormService.deleteEvaluationForm(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
