package com.example.quality_management_service.form.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Severity {
    @Id
    private Long id;

    private String name;
    private String description;
    private Double threshold;

}


