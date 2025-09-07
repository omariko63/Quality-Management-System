package com.example.quality_management_service.form.service;

import com.example.quality_management_service.form.dto.ProjectDto;
import com.example.quality_management_service.form.mapper.ProjectMapper;
import com.example.quality_management_service.form.model.Project;
import com.example.quality_management_service.form.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @Transactional
    public ProjectDto createProject(ProjectDto projectDTO) {
        Project project = projectMapper.toEntity(projectDTO);
        Project savedProject = projectRepository.save(project);
        return projectMapper.toDTO(savedProject);
    }

    @PreAuthorize("hasAnyRole('QA','QA_SUPERVISOR')")
    @Transactional(readOnly = true)
    public Optional<ProjectDto> getProject(Long id) {
        return projectRepository.findById(id)
                .map(projectMapper::toDTO);
    }

    @PreAuthorize("hasAnyRole('QA','QA_SUPERVISOR')")
    @Transactional(readOnly = true)
    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(projectMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @Transactional
    public Optional<ProjectDto> updateProject(Long id, ProjectDto projectDTO) {
        return projectRepository.findById(id)
                .map(project -> {
                    project.setName(projectDTO.name());
                    project.setDescription(projectDTO.description());
                    Project updatedProject = projectRepository.save(project);
                    return projectMapper.toDTO(updatedProject);
                });
    }

    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @Transactional
    public boolean deleteProject(Long id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return true;
        }
        return false;
    }
}