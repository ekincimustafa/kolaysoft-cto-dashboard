package com.kolaysoft.ctodashboard.config;

import com.kolaysoft.ctodashboard.entity.Project;
import com.kolaysoft.ctodashboard.entity.User;
import com.kolaysoft.ctodashboard.entity.WeeklyReport;
import com.kolaysoft.ctodashboard.entity.WorkItem;
import com.kolaysoft.ctodashboard.entity.RiskIssue;
import com.kolaysoft.ctodashboard.entity.enums.*;
import com.kolaysoft.ctodashboard.repository.ProjectRepository;
import com.kolaysoft.ctodashboard.repository.UserRepository;
import com.kolaysoft.ctodashboard.repository.WeeklyReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final WeeklyReportRepository weeklyReportRepository;

    @Override
    public void run(String... args) {
        // Eğer veritabanında zaten veri varsa tekrar ekleme yapma
        if (userRepository.count() > 0) {
            return;
        }

        System.out.println(">>> Başlangıç test verileri yükleniyor...");

        // 1. Kullanıcıları Oluştur
        User admin = User.builder()
                .email("admin@kolaysoft.com.tr")
                .passwordHash("admin123") // Not: İleride BCrypt ile şifreleyeceğiz
                .firstName("Sistem")
                .lastName("Yöneticisi")
                .role(RoleType.ADMIN)
                .build();

        User pm = User.builder()
                .email("pm@kolaysoft.com.tr")
                .passwordHash("pm123")
                .firstName("Ahmet")
                .lastName("Yılmaz")
                .role(RoleType.PROJECT_MANAGER)
                .build();

        User cto = User.builder()
                .email("cto@kolaysoft.com.tr")
                .passwordHash("cto123")
                .firstName("Mehmet")
                .lastName("Öztürk")
                .role(RoleType.CTO)
                .build();

        userRepository.saveAll(List.of(admin, pm, cto));

        // 2. PM'e Atanmış Projeleri Oluştur
        Project peyk = Project.builder()
                .name("PEYK - Bordro ve İK Sistemi")
                .clientName("Kolaysoft Kurumsal")
                .status(ProjectStatus.ACTIVE)
                .manager(pm)
                .build();

        Project eczaciPos = Project.builder()
                .name("EczacıPOS")
                .clientName("Sağlık Sektörü Grubu")
                .status(ProjectStatus.ACTIVE)
                .manager(pm)
                .build();

        projectRepository.saveAll(List.of(peyk, eczaciPos));

        // 3. PEYK Projesi İçin Örnek Bir Haftalık Rapor ve Alt Kalemleri Oluştur
        WeeklyReport sampleReport = WeeklyReport.builder()
                .project(peyk)
                .year(2026)
                .weekNumber(35)
                .reportDate(LocalDate.now())
                .targetProgress(85)
                .actualProgress(80)
                .status(ReportStatus.IN_PROGRESS)
                .scheduleStatus(ScheduleStatus.ON_TIME)
                .riskLevel(RiskLevel.LOW)
                .activeTaskCount(4)
                .summaryDone("Bordro imzalama API entegrasyonu tamamlandı.")
                .summaryTodo("E-imza mobil kütüphane testleri gerçekleştirilecek.")
                .notes("Genel takvim planlandığı gibi devam ediyor.")
                .isLocked(false)
                .build();

        // Raporun içine bir iş kalemi iliştiriyoruz
        WorkItem workItem = WorkItem.builder()
                .report(sampleReport)
                .title("Mobil SDK Güncellemesi")
                .assigneeName("Caner Demir")
                .status(WorkItemStatus.IN_PROGRESS)
                .dueDate(LocalDate.now().plusDays(3))
                .build();

        // Raporun içine bir risk kaydı iliştiriyoruz
        RiskIssue risk = RiskIssue.builder()
                .report(sampleReport)
                .title("Entegrasyon Test Ortamı Gecikmesi")
                .impactLevel(RiskLevel.MEDIUM)
                .actionPlan("Test sunucusu için ek kaynak talep edildi.")
                .ownerName("Ahmet Yılmaz")
                .build();

        sampleReport.getWorkItems().add(workItem);
        sampleReport.getRiskIssues().add(risk);

        // Aggregate Root mantığı: Yalnızca raporu kaydediyoruz,
        // CascadeType.ALL sayesinde workItem ve risk de otomatik veritabanına kaydedilir!
        weeklyReportRepository.save(sampleReport);

        System.out.println(">>> Başlangıç test verileri başarıyla PostgreSQL'e yüklendi!");
    }
}