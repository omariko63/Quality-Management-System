package com.example.quality_management_service.form.repository;

import com.example.quality_management_service.form.model.EvaluationForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationFormRepository extends JpaRepository<EvaluationForm,Long> {
}
