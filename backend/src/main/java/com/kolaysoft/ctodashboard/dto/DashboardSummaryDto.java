package com.kolaysoft.ctodashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardSummaryDto {
    private long totalProjects;
    private long activeProjects;
    private long delayedProjects;       // ScheduleStatus == DELAYED olanlar
    private long highRiskProjects;      // RiskLevel == HIGH veya CRITICAL olanlar
    private double averageProgress;     // Portföy genel ilerleme ortalaması
    private List<WeeklyReportResponseDto> latestReports; // En güncel haftalık durumlar tablosu
}