package com.kolaysoft.ctodashboard.dto;

import com.kolaysoft.ctodashboard.entity.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponseDto {
    private Long id;
    private String name;
    private String clientName;
    private ProjectStatus status;
    private Long managerId;
    private String managerName;
}