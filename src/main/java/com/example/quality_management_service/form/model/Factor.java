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
@Table(name = "factor")
public class Factor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "category_id")
	private Long categoryId;

	@Column(name = "question_text", columnDefinition = "TEXT")
	private String questionText;

	@Column(name = "weight", precision = 2, scale = 2)
	private BigDecimal weight;

	@Enumerated(EnumType.STRING)
	@Column(name = "answer_type")
	private AnswerType answerType;

	@Column(name = "notes", columnDefinition = "TEXT")
	private String notes;

	public enum AnswerType {
		TEXT,
		NUMBER,
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public Long getCategoryId() { return categoryId; }
	public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

	public String getQuestionText() { return questionText; }
	public void setQuestionText(String questionText) { this.questionText = questionText; }

	public BigDecimal getWeight() { return weight; }
	public void setWeight(BigDecimal weight) { this.weight = weight; }

	public AnswerType getAnswerType() { return answerType; }
	public void setAnswerType(AnswerType answerType) { this.answerType = answerType; }

	public String getNotes() { return notes; }
	public void setNotes(String notes) { this.notes = notes; }
}
