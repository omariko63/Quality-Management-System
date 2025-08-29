package com.example.quality_management_service.form.service;

import com.example.quality_management_service.form.dto.AnswerOptionDto;
import com.example.quality_management_service.form.mapper.AnswerOptionMapper;
import com.example.quality_management_service.form.model.AnswerOption;
import com.example.quality_management_service.form.model.Factor;
import com.example.quality_management_service.form.repository.AnswerOptionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerOptionService {

    private final AnswerOptionRepository repository;
    private final AnswerOptionMapper mapper;

    public AnswerOptionService(AnswerOptionRepository repository, AnswerOptionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<AnswerOptionDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public AnswerOptionDto getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("AnswerOption not found with id " + id));
    }

    public AnswerOptionDto create(AnswerOptionDto dto) {
        AnswerOption entity = mapper.toEntity(dto);

        if (dto.factorId() != null) {
            Factor factor = new Factor();
            factor.setId(dto.factorId());
            entity.setFactor(factor);
        }

        AnswerOption saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    public AnswerOptionDto update(Long id, AnswerOptionDto dto) {
        AnswerOption existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AnswerOption not found with id " + id));

        existing.setValue(dto.value());
        existing.setLabel(dto.label());
        existing.setPassing(dto.isPassing());
        existing.setWeight(dto.weight());

        // Update factor if provided
        if (dto.factorId() != null) {
            if (existing.getFactor() == null || !existing.getFactor().getId().equals(dto.factorId())) {
                Factor factor = new Factor();
                factor.setId(dto.factorId());
                existing.setFactor(factor);
            }
        }

        AnswerOption updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("AnswerOption not found with id " + id);
        }
        repository.deleteById(id);
    }
}
