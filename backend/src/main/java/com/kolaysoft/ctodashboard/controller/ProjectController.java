package com.kolaysoft.ctodashboard.controller;

import com.kolaysoft.ctodashboard.dto.ProjectResponseDto;
import com.kolaysoft.ctodashboard.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // React portuna izin veriyoruz
public class ProjectController {

    private final ProjectService projectService;

    // GET http://localhost:8080/api/projects
    // Opsiyonel filtre: http://localhost:8080/api/projects?managerId=2
    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> getProjects(
            @RequestParam(required = false) Long managerId) {

        if (managerId != null) {
            return ResponseEntity.ok(projectService.getProjectsByManager(managerId));
        }
        return ResponseEntity.ok(projectService.getAllProjects());
    }
}