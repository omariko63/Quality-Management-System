package com.example.quality_management_service.form.service;

import com.example.quality_management_service.form.dto.EvaluationFormDTO;
import com.example.quality_management_service.form.mapper.EvaluationFormMapper;
import com.example.quality_management_service.form.model.*;
import com.example.quality_management_service.form.repository.CategoryRepository;
import com.example.quality_management_service.form.repository.EvaluationFormRepository;
import com.example.quality_management_service.form.repository.ProjectRepository;
import com.example.quality_management_service.form.repository.SuccessCriteriaRepository;
import com.example.quality_management_service.management.model.User;
import com.example.quality_management_service.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvaluationFormService {

    private final EvaluationFormRepository evaluationFormRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final SuccessCriteriaRepository successCriteriaRepository;
    private final EvaluationFormMapper evaluationFormMapper;

    // CREATE
    @Transactional
    public EvaluationFormDTO createEvaluationForm(EvaluationFormDTO dto) {
        EvaluationForm form = evaluationFormMapper.toEntity(dto);

        // Set project
        Project project = projectRepository.findById(dto.projectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));
        form.setProject(project);

        // Set supervisor
        User supervisor = userRepository.findById(dto.supervisorId())
                .orElseThrow(() -> new RuntimeException("Supervisor not found"));
        form.setSupervisor(supervisor);

        // Set timestamps
        Instant now = Instant.now();
        form.setCreatedAt(now);
        form.setUpdatedAt(now);

        // Optionally set categories
        if (dto.categoryIds() != null) {
            List<Category> categories = categoryRepository.findAllById(dto.categoryIds());
            if(!validateSeverityWeight(categories)) {
                throw new IllegalArgumentException("Severity weights not valid");
            }
            form.setCategories(categories);
            for(Category category : categories) {
                category.setForm(form);
            }
            //form.setCategories(categories);
            //categories.forEach(c -> c.setForm(form));
        }

        // Optionally set success criteria
        if (dto.successCriteriaIds() != null) {
            List<SuccessCriteria> criteria = successCriteriaRepository.findAllById(dto.successCriteriaIds());
            form.setSuccessCriteria(criteria);
            criteria.forEach(c -> c.setEvaluationForm(form));
        }

        EvaluationForm saved = evaluationFormRepository.save(form);
        return evaluationFormMapper.toDTO(saved);
    }

    // READ - by ID
    @Transactional(readOnly = true)
    public Optional<EvaluationFormDTO> getEvaluationForm(Long id) {
        return evaluationFormRepository.findById(id)
                .map(evaluationFormMapper::toDTO);
    }

    // READ - all
    @Transactional(readOnly = true)
    public List<EvaluationFormDTO> getAllEvaluationForms() {
        return evaluationFormRepository.findAll()
                .stream()
                .map(evaluationFormMapper::toDTO)
                .collect(Collectors.toList());
    }

    // UPDATE
    @Transactional
    public Optional<EvaluationFormDTO> updateEvaluationForm(Long id, EvaluationFormDTO dto) {
        return evaluationFormRepository.findById(id)
                .map(form -> {
                    // MapStruct will update only non-null fields
                    evaluationFormMapper.updateEntityFromDto(dto, form);

                    form.setUpdatedAt(Instant.now());

                    EvaluationForm updated = evaluationFormRepository.save(form);
                    return evaluationFormMapper.toDTO(updated);
                });
    }



    // DELETE
    @Transactional
    public boolean deleteEvaluationForm(Long id) {
        return evaluationFormRepository.findById(id)
                .map(form -> {
                    evaluationFormRepository.delete(form);
                    return true;
                })
                .orElse(false);
    }


    private boolean validateCategoryWeight( Category category){
        BigDecimal categoryWeight = category.calculateWeight();
        return categoryWeight.compareTo(BigDecimal.valueOf(100)) == 0;
    }
    private boolean validateSeverityWeight(List<Category> categories){
        Map<Severity, List<Category>> severityGroups = createSeverityMap(categories);
        for(Map.Entry<Severity, List<Category>> entry : severityGroups.entrySet()){
            Severity severity = entry.getKey();
            List<Category> categoryList = entry.getValue();

            BigDecimal totalWeight = BigDecimal.ZERO;
            for(Category category : categoryList){
                totalWeight =  totalWeight.add(category.getWeight());
            }
            if(totalWeight.compareTo(BigDecimal.valueOf(100)) != 0){
                return false;
            }
        }
        return true;
    }
    private Map<Severity, List<Category>> createSeverityMap(List<Category> categories){
        Map<Severity, List<Category>> severityGroups = new HashMap<>();
        for(Category category : categories){
            Severity severity = category.getSeverity();
            if(!severityGroups.containsKey(severity)){
                severityGroups.put(severity, new ArrayList<>());
            }
            severityGroups.get(severity).add(category);
        }
        return severityGroups;
    }
}
