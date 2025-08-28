package com.example.quality_management_service.form.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "evaluation_form_id", nullable = false)
	private EvaluationForm evaluationForm;

	@Column(name = "title")
	private String title;

	@Column(name = "weight", precision = 19, scale = 2)
	private BigDecimal weight;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "severity_id", nullable = false)
	private Severity severity;

	// Getters and setters
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public EvaluationForm getEvaluationForm() { return evaluationForm; }
	public void setEvaluationForm(EvaluationForm evaluationForm) { this.evaluationForm = evaluationForm; }

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }

	public BigDecimal getWeight() { return weight; }
	public void setWeight(BigDecimal weight) { this.weight = weight; }

	public Severity getSeverity() { return severity; }
	public void setSeverity(Severity severity) { this.severity = severity; }
}
