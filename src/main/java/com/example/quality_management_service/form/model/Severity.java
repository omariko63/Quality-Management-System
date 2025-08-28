package com.example.quality_management_service.form.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "severity",
       uniqueConstraints = @UniqueConstraint(name="uq_severity_name", columnNames = "name"))
public class Severity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    private String description;

    @Column(precision = 6, scale = 2)
    private BigDecimal threshold;

    @OneToMany(mappedBy = "severity")
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "severity")
    private List<SuccessCriteria> successCriteria = new ArrayList<>();

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getThreshold() { return threshold; }
    public void setThreshold(BigDecimal threshold) { this.threshold = threshold; }

    public List<Category> getCategories() { return categories; }
    public void setCategories(List<Category> categories) { this.categories = categories; }

    public List<SuccessCriteria> getSuccessCriteria() { return successCriteria; }
    public void setSuccessCriteria(List<SuccessCriteria> successCriteria) { this.successCriteria = successCriteria; }
}
