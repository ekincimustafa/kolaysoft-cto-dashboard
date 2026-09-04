package com.kolaysoft.ctodashboard.service;

import com.kolaysoft.ctodashboard.dto.ProjectResponseDto;
import com.kolaysoft.ctodashboard.entity.Project;
import com.kolaysoft.ctodashboard.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    // Tüm projeleri DTO listesi olarak getirir (CTO / Admin görünümü)
    @Transactional(readOnly = true)
    public List<ProjectResponseDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Yalnızca belirli bir yöneticiye ait projeleri getirir (PM görünümü - ADR 05)
    @Transactional(readOnly = true)
    public List<ProjectResponseDto> getProjectsByManager(Long managerId) {
        return projectRepository.findByManagerId(managerId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Entity -> DTO Dönüştürücü (Helper Metot)
    private ProjectResponseDto mapToDto(Project project) {
        String fullName = project.getManager() != null
                ? project.getManager().getFirstName() + " " + project.getManager().getLastName()
                : "Atanmamış";

        return ProjectResponseDto.builder()
                .id(project.getId())
                .name(project.getName())
                .clientName(project.getClientName())
                .status(project.getStatus())
                .managerId(project.getManager() != null ? project.getManager().getId() : null)
                .managerName(fullName)
                .build();
    }
}