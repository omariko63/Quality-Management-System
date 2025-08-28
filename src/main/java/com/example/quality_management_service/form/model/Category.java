package com.example.quality_management_service.form.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "evaluation_form_id")
	private Long evaluationFormId;

	@Column(name = "title")
	private String title;

	@Column(name = "weight", precision = 2, scale = 2)
	private BigDecimal weight;

	@Column(name = "severity_id")
	private Long severityId;

	// Getters and setters
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public Long getEvaluationFormId() { return evaluationFormId; }
	public void setEvaluationFormId(Long evaluationFormId) { this.evaluationFormId = evaluationFormId; }

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }

	public BigDecimal getWeight() { return weight; }
	public void setWeight(BigDecimal weight) { this.weight = weight; }

	public Long getSeverityId() { return severityId; }
	public void setSeverityId(Long severityId) { this.severityId = severityId; }
}
