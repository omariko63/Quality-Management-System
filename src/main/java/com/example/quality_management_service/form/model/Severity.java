package com.example.quality_management_service.form.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Severity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double threshold;

}


