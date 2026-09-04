package com.kolaysoft.ctodashboard.repository;

import com.kolaysoft.ctodashboard.entity.WeeklyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeeklyReportRepository extends JpaRepository<WeeklyReport, Long> {

    // Bir projenin geçmiş tüm haftalık raporlarını kronolojik listelemek için
    List<WeeklyReport> findByProjectIdOrderByYearDescWeekNumberDesc(Long projectId);

    // Tekillik Kuralı: Aynı projede aynı yıl ve haftada rapor var mı kontrolü
    Optional<WeeklyReport> findByProjectIdAndYearAndWeekNumber(Long projectId, Integer year, Integer weekNumber);

    // Rapor oluşturmadan önce var olup olmadığını hızlıca kontrol etmek için
    boolean existsByProjectIdAndYearAndWeekNumber(Long projectId, Integer year, Integer weekNumber);
}