package com.kolaysoft.ctodashboard.controller;

import com.kolaysoft.ctodashboard.dto.WeeklyReportCreateDto;
import com.kolaysoft.ctodashboard.dto.WeeklyReportResponseDto;
import com.kolaysoft.ctodashboard.service.WeeklyReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class WeeklyReportController {

    private final WeeklyReportService weeklyReportService;

    // POST http://localhost:8080/api/reports -> Yeni Rapor Kaydet
    @PostMapping
    public ResponseEntity<WeeklyReportResponseDto> createReport(
            @Valid @RequestBody WeeklyReportCreateDto dto) {
        WeeklyReportResponseDto created = weeklyReportService.createReport(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET http://localhost:8080/api/reports/project/{projectId} -> Projenin Tüm Raporları
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<WeeklyReportResponseDto>> getReportsByProject(
            @PathVariable Long projectId) {
        return ResponseEntity.ok(weeklyReportService.getReportsByProject(projectId));
    }

    // GET http://localhost:8080/api/reports/{id} -> Tekil Rapor Detayı
    @GetMapping("/{id}")
    public ResponseEntity<WeeklyReportResponseDto> getReportById(
            @PathVariable Long id) {
        return ResponseEntity.ok(weeklyReportService.getReportById(id));
    }
}