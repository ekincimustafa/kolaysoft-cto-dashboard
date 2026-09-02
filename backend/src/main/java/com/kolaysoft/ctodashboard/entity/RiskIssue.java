package com.kolaysoft.ctodashboard.entity;

import com.kolaysoft.ctodashboard.entity.enums.RiskLevel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "risk_issues")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", nullable = false)
    private WeeklyReport report;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RiskLevel impactLevel;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String actionPlan;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}