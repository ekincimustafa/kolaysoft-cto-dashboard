package com.kolaysoft.ctodashboard.dto;

import com.kolaysoft.ctodashboard.entity.enums.ReportStatus;
import com.kolaysoft.ctodashboard.entity.enums.RiskLevel;
import com.kolaysoft.ctodashboard.entity.enums.ScheduleStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeeklyReportCreateDto {

    @NotNull(message = "Proje ID zorunludur")
    private Long projectId;

    @NotNull(message = "Hafta numarası zorunludur")
    @Min(value = 1, message = "Hafta numarası 1'den küçük olamaz")
    @Max(value = 53, message = "Hafta numarası 53'ten büyük olamaz")
    private Integer weekNumber;

    @NotNull(message = "Yıl bilgisi zorunludur")
    private Integer year;

    @NotNull(message = "Rapor tarihi zorunludur")
    private LocalDate reportDate;

    @NotNull(message = "Hedeflenen ilerleme zorunludur")
    @Min(value = 0, message = "Hedeflenen ilerleme 0'dan küçük olamaz")
    @Max(value = 100, message = "Hedeflenen ilerleme 100'den büyük olamaz")
    private Integer targetProgress;

    @NotNull(message = "Gerçekleşen ilerleme zorunludur")
    @Min(value = 0, message = "Gerçekleşen ilerleme 0'dan küçük olamaz")
    @Max(value = 100, message = "Gerçekleşen ilerleme 100'den büyük olamaz")
    private Integer actualProgress;

    @NotNull(message = "Genel durum seçilmelidir")
    private ReportStatus status;

    @NotNull(message = "Takvim durumu seçilmelidir")
    private ScheduleStatus scheduleStatus;

    @NotNull(message = "Risk seviyesi seçilmelidir")
    private RiskLevel riskLevel;

    @NotNull(message = "Canlı task sayısı zorunludur")
    @Min(value = 0, message = "Canlı task sayısı negatif olamaz")
    private Integer activeTaskCount;

    @NotBlank(message = "Yapılanlar özeti boş bırakılamaz")
    @Size(min = 10, message = "Yapılanlar özeti en az 10 karakter olmalıdır")
    private String summaryDone;

    @NotBlank(message = "Yapılacaklar özeti boş bırakılamaz")
    @Size(min = 10, message = "Yapılacaklar özeti en az 10 karakter olmalıdır")
    private String summaryTodo;

    private String notes;

    @Valid
    @Builder.Default
    private List<WorkItemDto> workItems = new ArrayList<>();

    @Valid
    @Builder.Default
    private List<RiskIssueDto> riskIssues = new ArrayList<>();
}