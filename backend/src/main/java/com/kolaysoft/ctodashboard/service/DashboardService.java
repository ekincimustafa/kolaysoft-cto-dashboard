package com.kolaysoft.ctodashboard.service;

import com.kolaysoft.ctodashboard.dto.DashboardSummaryDto;
import com.kolaysoft.ctodashboard.dto.WeeklyReportResponseDto;
import com.kolaysoft.ctodashboard.entity.Project;
import com.kolaysoft.ctodashboard.entity.WeeklyReport;
import com.kolaysoft.ctodashboard.entity.enums.ProjectStatus;
import com.kolaysoft.ctodashboard.entity.enums.RiskLevel;
import com.kolaysoft.ctodashboard.entity.enums.ScheduleStatus;
import com.kolaysoft.ctodashboard.repository.ProjectRepository;
import com.kolaysoft.ctodashboard.repository.WeeklyReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ProjectRepository projectRepository;
    private final WeeklyReportRepository weeklyReportRepository;
    private final WeeklyReportService weeklyReportService;

    @Transactional(readOnly = true)
    public DashboardSummaryDto getCtoDashboardSummary() {
        List<Project> allProjects = projectRepository.findAll();

        long totalProjects = allProjects.size();
        long activeProjects = allProjects.stream()
                .filter(p -> p.getStatus() == ProjectStatus.ACTIVE)
                .count();

        long delayedCount = 0;
        long highRiskCount = 0;
        double totalProgress = 0;
        int reportedProjectCount = 0;

        List<WeeklyReportResponseDto> latestReports = new ArrayList<>();

        // Her projenin en son girilen raporunu bulup portföy sağlığını hesaplıyoruz
        for (Project project : allProjects) {
            List<WeeklyReport> reports = weeklyReportRepository
                    .findByProjectIdOrderByYearDescWeekNumberDesc(project.getId());

            if (!reports.isEmpty()) {
                WeeklyReport latest = reports.get(0); // En son haftanın raporu

                if (latest.getScheduleStatus() == ScheduleStatus.DELAYED) {
                    delayedCount++;
                }

                if (latest.getRiskLevel() == RiskLevel.HIGH || latest.getRiskLevel() == RiskLevel.CRITICAL) {
                    highRiskCount++;
                }

                totalProgress += latest.getActualProgress();
                reportedProjectCount++;

                // Tablo için DTO listesine ekliyoruz
                latestReports.add(weeklyReportService.getReportById(latest.getId()));
            }
        }

        double averageProgress = reportedProjectCount > 0 
                ? Math.round((totalProgress / reportedProjectCount) * 100.0) / 100.0 
                : 0.0;

        return DashboardSummaryDto.builder()
                .totalProjects(totalProjects)
                .activeProjects(activeProjects)
                .delayedProjects(delayedCount)
                .highRiskProjects(highRiskCount)
                .averageProgress(averageProgress)
                .latestReports(latestReports)
                .build();
    }
}