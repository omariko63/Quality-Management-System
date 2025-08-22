package com.example.quality_management_service.management.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String roleName;

    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // One Role -> Many Users (inverse side)
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<User> users = new ArrayList<>();

    // Many-to-Many: Role ↔ Permission
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();

    public Role(String roleName, String description) {
        this.roleName = roleName;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }

    public void addUser(User user) {
        users.add(user);
        user.setRole(this);
    }

    public void removeUser(User user) {
        users.remove(user);
        user.setRole(null);
    }

    public void addPermission(Permission permission) {
        permissions.add(permission);
    }

    public void removePermission(Permission permission) {
        permissions.remove(permission);
    }
}
