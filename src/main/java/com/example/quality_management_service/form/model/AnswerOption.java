package com.example.quality_management_service.form.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AnswerOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "factor_id")
    private Factor factor;

    private Integer value;
    private String label;
    private Boolean isPassing;
    private Double weight;
}