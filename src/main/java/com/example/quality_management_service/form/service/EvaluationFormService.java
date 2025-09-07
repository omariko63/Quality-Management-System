package com.example.quality_management_service.form.service;

import com.example.quality_management_service.form.dto.EvaluationFormDTO;
import com.example.quality_management_service.form.dto.FullFormCreateRequest;
import com.example.quality_management_service.form.dto.EvaluationFormBulkCreateDto;
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

import com.example.quality_management_service.form.dto.SeverityDto;
import com.example.quality_management_service.form.dto.CategoryDto;
import com.example.quality_management_service.form.dto.FactorDto;
import com.example.quality_management_service.form.dto.AnswerOptionDto;
import com.example.quality_management_service.form.dto.SuccessCriteriaDto;
import com.example.quality_management_service.form.repository.SeverityRepository;
import com.example.quality_management_service.form.repository.FactorRepository;
import com.example.quality_management_service.form.repository.AnswerOptionRepository;

@Service
@RequiredArgsConstructor
public class EvaluationFormService {

    private final EvaluationFormRepository evaluationFormRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final SuccessCriteriaRepository successCriteriaRepository;
    private final EvaluationFormMapper evaluationFormMapper;
    private final SeverityRepository severityRepository;
    private final FactorRepository factorRepository;
    private final AnswerOptionRepository answerOptionRepository;

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

    @Transactional
    public Map<String, Object> createFullForm(FullFormCreateRequest request) {
        Map<String, Object> result = new HashMap<>();
        // 1. Create Project
        Project project = null;
        if (request.project != null) {
            project = new Project();
            project.setName(request.project.name());
            project.setDescription(request.project.description());
            project = projectRepository.save(project);
            result.put("projectId", project.getId());
        }
        // 2. Create Severities
        List<Long> severityIds = new ArrayList<>();
        if (request.severities != null) {
            for (SeverityDto s : request.severities) {
                Severity severity = new Severity();
                severity.setName(s.name());
                severity.setDescription(s.description());
                severity = severityRepository.save(severity);
                severityIds.add(severity.getId());
            }
            result.put("severityIds", severityIds);
        }
        // 3. Create EvaluationForm
        EvaluationFormBulkCreateDto formDto = request.form;
        EvaluationForm form = evaluationFormMapper.toEntity(formDto);
        if (project != null) form.setProject(project);
        if (formDto.supervisorId() != null) {
            User supervisor = userRepository.findById(formDto.supervisorId())
                .orElseThrow(() -> new RuntimeException("Supervisor not found"));
            form.setSupervisor(supervisor);
        }
        form.setCreatedAt(Instant.now());
        form.setUpdatedAt(Instant.now());
        // 4. Create Categories from DTOs and add to form
        List<Long> categoryIds = new ArrayList<>();
        if (formDto.categories() != null) {
            for (CategoryDto catDto : formDto.categories()) {
                Category category = new Category();
                category.setForm(form); // Set the form reference
                category.setTitle(catDto.title());
                category.setWeight(catDto.weight());
                if (catDto.severityId() != null) {
                    Severity severity = severityRepository.findById(catDto.severityId())
                        .orElse(null);
                    category.setSeverity(severity);
                }
                // Handle factors if present
                if (catDto.factors() != null) {
                    List<Factor> factors = new ArrayList<>();
                    for (FactorDto factorDto : catDto.factors()) {
                        Factor factor = new Factor();
                        factor.setCategory(category); // Set parent category
                        factor.setQuestionText(factorDto.questionText());
                        factor.setWeight(factorDto.weight());
                        factor.setAnswerType(factorDto.answerType());
                        factors.add(factor);
                    }
                    category.setFactors(factors);
                }
                form.getCategories().add(category); // Add to form's categories list
            }
        }
        form = evaluationFormRepository.save(form); // Save form and cascade categories
        result.put("formId", form.getId());
        // Collect category IDs
        for (Category category : form.getCategories()) {
            categoryIds.add(category.getId());
        }
        result.put("categoryIds", categoryIds);
        // 7. Create SuccessCriteria
        List<Long> criteriaIds = new ArrayList<>();
        if (formDto.successCriteria() != null) {
            for (SuccessCriteriaDto scDto : formDto.successCriteria()) {
                SuccessCriteria sc = new SuccessCriteria();
                sc.setEvaluationForm(form);
                if (scDto.severityId() != null) {
                    Severity severity = severityRepository.findById(scDto.severityId())
                        .orElse(null);
                    sc.setSeverity(severity);
                }
                sc.setThreshold(java.math.BigDecimal.valueOf(scDto.threshold()));
                sc = successCriteriaRepository.save(sc);
                criteriaIds.add(sc.getId());
            }
        }
        result.put("successCriteriaIds", criteriaIds);
        return result;
    }
}
