package com.example.quality_management_service.form.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "success_criteria")
public class Success_criteria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "evaluation_form_id")
	private Long evaluationFormId;

	@Column(name = "severity_id")
	private Long severityId;

	@Enumerated(EnumType.STRING)
	@Column(name = "method")
	private Method method;

	@Column(name = "threshold", precision = 19, scale = 2)
	private BigDecimal threshold;

	public enum Method {
		PERCENTAGE,
		COUNT
	}

	// Getters and setters
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public Long getEvaluationFormId() { return evaluationFormId; }
	public void setEvaluationFormId(Long evaluationFormId) { this.evaluationFormId = evaluationFormId; }

	public Long getSeverityId() { return severityId; }
	public void setSeverityId(Long severityId) { this.severityId = severityId; }

	public Method getMethod() { return method; }
	public void setMethod(Method method) { this.method = method; }

	public BigDecimal getThreshold() { return threshold; }
	public void setThreshold(BigDecimal threshold) { this.threshold = threshold; }
}
