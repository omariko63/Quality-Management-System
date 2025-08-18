package com.example.quality_management_service.controller;

import com.example.quality_management_service.dto.RoleDto;
import com.example.quality_management_service.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public Map<String, Object> create(@RequestBody RoleDto dto) {
        return roleService.create(dto);
    }

    @GetMapping
    public Map<String, Object> findAll() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public Map<String, Object> findById(@PathVariable Integer id) {
        return roleService.findById(id);
    }

    @PutMapping("/{id}")
    public Map<String, Object> update(@PathVariable Integer id, @RequestBody RoleDto dto) {
        return roleService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable Integer id) {
        return roleService.delete(id);
    }
}
