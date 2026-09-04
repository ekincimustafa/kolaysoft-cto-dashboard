package com.kolaysoft.ctodashboard.dto;

import com.kolaysoft.ctodashboard.entity.enums.WorkItemStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkItemDto {
    private Long id;

    @NotBlank(message = "İş kalemi başlığı boş bırakılamaz")
    private String title;

    @NotBlank(message = "Sorumlu kişi boş bırakılamaz")
    private String assigneeName;

    @NotNull(message = "İş kalemi durumu zorunludur")
    private WorkItemStatus status;

    private LocalDate dueDate;
}