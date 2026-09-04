package com.kolaysoft.ctodashboard.dto;

import com.kolaysoft.ctodashboard.entity.enums.RiskLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskIssueDto {
    private Long id;

    @NotBlank(message = "Risk tanımı boş bırakılamaz")
    private String title;

    @NotNull(message = "Risk etki seviyesi zorunludur")
    private RiskLevel impactLevel;

    @NotBlank(message = "Aksiyon planı boş bırakılamaz")
    private String actionPlan;

    @NotBlank(message = "Risk sorumlusu boş bırakılamaz")
    private String ownerName;
}