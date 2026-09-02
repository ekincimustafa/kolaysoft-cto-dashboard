package com.kolaysoft.ctodashboard.entity;

import com.kolaysoft.ctodashboard.entity.enums.ReportStatus;
import com.kolaysoft.ctodashboard.entity.enums.RiskLevel;
import com.kolaysoft.ctodashboard.entity.enums.ScheduleStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "weekly_reports",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"project_id", "year", "week_number"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeeklyReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "week_number", nullable = false)
    private Integer weekNumber;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private LocalDate reportDate;

    @Column(nullable = false)
    private Integer targetProgress;

    @Column(nullable = false)
    private Integer actualProgress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleStatus scheduleStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RiskLevel riskLevel;

    @Column(nullable = false)
    private Integer activeTaskCount;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String summaryDone;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String summaryTodo;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isLocked = false;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<WorkItem> workItems = new ArrayList<>();

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<RiskIssue> riskIssues = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}