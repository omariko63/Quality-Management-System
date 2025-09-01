package com.example.quality_management_service.form.service;

import com.example.quality_management_service.form.dto.FactorDto;
import com.example.quality_management_service.form.model.Factor;
import com.example.quality_management_service.form.model.Category;
import com.example.quality_management_service.form.repository.FactorRepository;
import com.example.quality_management_service.form.repository.CategoryRepository;
import com.example.quality_management_service.form.mapper.FactorMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.persistence.EntityNotFoundException;

@Service
public class FactorService {
    private final FactorRepository factorRepository;
    private final FactorMapper factorMapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public FactorService(FactorRepository factorRepository, FactorMapper factorMapper, CategoryRepository categoryRepository) {
        this.factorRepository = factorRepository;
        this.factorMapper = factorMapper;
        this.categoryRepository = categoryRepository;
    }

    public FactorDto createFactor(FactorDto factorDto) {
        Factor factor = factorMapper.toEntity(factorDto);
        Category category = categoryRepository.findById(factorDto.categoryId())
            .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        factor.setCategory(category);
        Factor saved = factorRepository.save(factor);
        return factorMapper.toDto(saved);
    }

    public List<FactorDto> getAllFactors() {
        return factorRepository.findAll().stream()
                .map(factorMapper::toDto)
                .collect(Collectors.toList());
    }

    public FactorDto getFactorById(Long id) {
        return factorRepository.findById(id)
                .map(factorMapper::toDto)
                .orElse(null);
    }

    public FactorDto updateFactor(Long id, FactorDto factorDto) {
        return factorRepository.findById(id)
                .map(existing -> {
                    Factor updated = factorMapper.toEntity(factorDto);
                    updated.setId(id);
                    return factorMapper.toDto(factorRepository.save(updated));
                })
                .orElse(null);
    }

    public void deleteFactor(Long id) {
        factorRepository.deleteById(id);
    }
}
