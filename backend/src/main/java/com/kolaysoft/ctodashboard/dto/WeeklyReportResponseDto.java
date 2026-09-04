package com.kolaysoft.ctodashboard.dto;

import com.kolaysoft.ctodashboard.entity.enums.ReportStatus;
import com.kolaysoft.ctodashboard.entity.enums.RiskLevel;
import com.kolaysoft.ctodashboard.entity.enums.ScheduleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeeklyReportResponseDto {
    private Long id;
    private Long projectId;
    private String projectName;
    private Integer weekNumber;
    private Integer year;
    private LocalDate reportDate;
    private Integer targetProgress;
    private Integer actualProgress;
    private ReportStatus status;
    private ScheduleStatus scheduleStatus;
    private RiskLevel riskLevel;
    private Integer activeTaskCount;
    private String summaryDone;
    private String summaryTodo;
    private String notes;
    private Boolean isLocked;
    private LocalDateTime createdAt;

    @Builder.Default
    private List<WorkItemDto> workItems = new ArrayList<>();

    @Builder.Default
    private List<RiskIssueDto> riskIssues = new ArrayList<>();
}