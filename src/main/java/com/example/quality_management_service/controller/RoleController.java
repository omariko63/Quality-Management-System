package com.example.quality_management_service.controller;

import com.example.quality_management_service.dto.RoleDto;
import com.example.quality_management_service.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public RoleDto create(@RequestBody RoleDto dto) {
        return roleService.create(dto);
    }

    @GetMapping
    public List<RoleDto> findAll() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public RoleDto findById(@PathVariable Integer id) {
        return roleService.findById(id);
    }

    @PutMapping("/{id}")
    public RoleDto update(@PathVariable Integer id, @RequestBody RoleDto dto) {
        return roleService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        roleService.delete(id);
    }
}
