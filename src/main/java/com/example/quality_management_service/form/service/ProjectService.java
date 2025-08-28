package com.example.quality_management_service.form.service;

import com.example.quality_management_service.form.dto.ProjectDTO;
import com.example.quality_management_service.form.mapper.ProjectMapper;
import com.example.quality_management_service.form.model.Project;
import com.example.quality_management_service.form.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Transactional
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        Project project = projectMapper.toEntity(projectDTO);
        Project savedProject = projectRepository.save(project);
        return projectMapper.toDTO(savedProject);
    }

    @Transactional(readOnly = true)
    public Optional<ProjectDTO> getProject(Long id) {
        return projectRepository.findById(id)
                .map(projectMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(projectMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<ProjectDTO> updateProject(Long id, ProjectDTO projectDTO) {
        return projectRepository.findById(id)
                .map(project -> {
                    project.setName(projectDTO.name());
                    project.setDescription(projectDTO.description());
                    Project updatedProject = projectRepository.save(project);
                    return projectMapper.toDTO(updatedProject);
                });
    }

    @Transactional
    public boolean deleteProject(Long id) {
        return projectRepository.findById(id)
                .map(project -> {
                    projectRepository.delete(project);
                    return true;
                })
                .orElse(false);
    }
}