package com.example.quality_management_service.management.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Permission() {}

    public Permission(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }



}
