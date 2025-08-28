package com.example.quality_management_service.form.model;

import jakarta.persistence.*;
import lombok.*;

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


