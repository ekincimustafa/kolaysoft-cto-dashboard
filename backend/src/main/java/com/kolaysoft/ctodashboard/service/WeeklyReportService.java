package com.kolaysoft.ctodashboard.service;

import com.kolaysoft.ctodashboard.dto.*;
import com.kolaysoft.ctodashboard.entity.Project;
import com.kolaysoft.ctodashboard.entity.RiskIssue;
import com.kolaysoft.ctodashboard.entity.WeeklyReport;
import com.kolaysoft.ctodashboard.entity.WorkItem;
import com.kolaysoft.ctodashboard.repository.ProjectRepository;
import com.kolaysoft.ctodashboard.repository.WeeklyReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeeklyReportService {

    private final WeeklyReportRepository weeklyReportRepository;
    private final ProjectRepository projectRepository;

    // 1. Yeni Haftalık Rapor Oluşturma (Aggregate Root Kaydı)
    @Transactional
    public WeeklyReportResponseDto createReport(WeeklyReportCreateDto dto) {
        // Kural 1: Gelecek tarihe rapor girilemez
        if (dto.getReportDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Rapor tarihi bugünden ileri bir tarih olamaz.");
        }

        // Kural 2: Proje var mı kontrolü
        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("Proje bulunamadı. ID: " + dto.getProjectId()));

        // Kural 3: Mükerrer rapor kontrolü (Tekillik Kuralı)
        if (weeklyReportRepository.existsByProjectIdAndYearAndWeekNumber(
                dto.getProjectId(), dto.getYear(), dto.getWeekNumber())) {
            throw new IllegalStateException("Bu proje için " + dto.getYear() + " yılı " + dto.getWeekNumber() + ". haftaya ait rapor zaten mevcut.");
        }

        // Ana Rapor Entity'sini oluşturma
        WeeklyReport report = WeeklyReport.builder()
                .project(project)
                .year(dto.getYear())
                .weekNumber(dto.getWeekNumber())
                .reportDate(dto.getReportDate())
                .targetProgress(dto.getTargetProgress())
                .actualProgress(dto.getActualProgress())
                .status(dto.getStatus())
                .scheduleStatus(dto.getScheduleStatus())
                .riskLevel(dto.getRiskLevel())
                .activeTaskCount(dto.getActiveTaskCount())
                .summaryDone(dto.getSummaryDone())
                .summaryTodo(dto.getSummaryTodo())
                .notes(dto.getNotes())
                .isLocked(false)
                .build();

        // Alt İş Kalemlerini (WorkItems) rapora bağlama
        if (dto.getWorkItems() != null) {
            for (WorkItemDto itemDto : dto.getWorkItems()) {
                WorkItem item = WorkItem.builder()
                        .report(report) // Kritik: İlişki sahibini belirliyoruz
                        .title(itemDto.getTitle())
                        .assigneeName(itemDto.getAssigneeName())
                        .status(itemDto.getStatus())
                        .dueDate(itemDto.getDueDate())
                        .build();
                report.getWorkItems().add(item);
            }
        }

        // Alt Riskleri (RiskIssues) rapora bağlama
        if (dto.getRiskIssues() != null) {
            for (RiskIssueDto riskDto : dto.getRiskIssues()) {
                RiskIssue risk = RiskIssue.builder()
                        .report(report) // Kritik: İlişki sahibini belirliyoruz
                        .title(riskDto.getTitle())
                        .impactLevel(riskDto.getImpactLevel())
                        .actionPlan(riskDto.getActionPlan())
                        .ownerName(riskDto.getOwnerName())
                        .build();
                report.getRiskIssues().add(risk);
            }
        }

        // CascadeType.ALL sayesinde raporla birlikte tüm iş kalemleri ve riskler tek seferde kaydedilir
        WeeklyReport savedReport = weeklyReportRepository.save(report);

        return mapToResponseDto(savedReport);
    }

    // 2. Bir Projeye Ait Geçmiş Raporları Listeleme
    @Transactional(readOnly = true)
    public List<WeeklyReportResponseDto> getReportsByProject(Long projectId) {
        return weeklyReportRepository.findByProjectIdOrderByYearDescWeekNumberDesc(projectId).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    // 3. Tek Bir Raporun Detayını Getirme
    @Transactional(readOnly = true)
    public WeeklyReportResponseDto getReportById(Long reportId) {
        WeeklyReport report = weeklyReportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("Rapor bulunamadı. ID: " + reportId));
        return mapToResponseDto(report);
    }

    // Entity -> DTO Dönüştürücü
    private WeeklyReportResponseDto mapToResponseDto(WeeklyReport report) {
        List<WorkItemDto> workItemDtos = report.getWorkItems().stream()
                .map(w -> WorkItemDto.builder()
                        .id(w.getId())
                        .title(w.getTitle())
                        .assigneeName(w.getAssigneeName())
                        .status(w.getStatus())
                        .dueDate(w.getDueDate())
                        .build())
                .collect(Collectors.toList());

        List<RiskIssueDto> riskIssueDtos = report.getRiskIssues().stream()
                .map(r -> RiskIssueDto.builder()
                        .id(r.getId())
                        .title(r.getTitle())
                        .impactLevel(r.getImpactLevel())
                        .actionPlan(r.getActionPlan())
                        .ownerName(r.getOwnerName())
                        .build())
                .collect(Collectors.toList());

        return WeeklyReportResponseDto.builder()
                .id(report.getId())
                .projectId(report.getProject().getId())
                .projectName(report.getProject().getName())
                .weekNumber(report.getWeekNumber())
                .year(report.getYear())
                .reportDate(report.getReportDate())
                .targetProgress(report.getTargetProgress())
                .actualProgress(report.getActualProgress())
                .status(report.getStatus())
                .scheduleStatus(report.getScheduleStatus())
                .riskLevel(report.getRiskLevel())
                .activeTaskCount(report.getActiveTaskCount())
                .summaryDone(report.getSummaryDone())
                .summaryTodo(report.getSummaryTodo())
                .notes(report.getNotes())
                .isLocked(report.getIsLocked())
                .createdAt(report.getCreatedAt())
                .workItems(workItemDtos)
                .riskIssues(riskIssueDtos)
                .build();
    }
}