# Günlük Çalışma ve Öğrenme Notları

## 1. Gün (1 Eylül 2026)

### 📌 Tamamlanan İşler
- **Gereksinim ve Kapsam Analizi:** `analiz.md` dokümanı v1.0 olarak tamamlandı; iş kuralları (proje görünürlüğü, manuel ilerleme hesabı, rapor kilitleme) proje sorumlusu Muhammet Fatih Diker ile netleştirilerek onaylandı.
- **Veritabanı Altyapısı:** Docker Compose ile PostgreSQL 16 servisi `5432` portunda izole konteyner olarak ayağa kaldırıldı ve veri kalıcılığı (volume) yapılandırıldı.
- **Backend İskeleti:** Spring Boot 3.x projesi Maven, Spring Data JPA, Lombok, Validation ve PostgreSQL Driver bağımlılıklarıyla kuruldu.
- **Health Check & CORS:** `/api/health` REST endpoint'i yazılarak veritabanı bağlantısı ve servis durumu doğrulandı.
- **Frontend İskeleti:** Vite + React 18 ortamı ayağa kaldırıldı, `App.jsx` üzerinden backend sağlık endpoint'ine fetch isteği atılarak uçtan uca bağlantı test edildi.
- **Versiyon Kontrolü & Dokümantasyon:** Tüm monorepo mimarisi, kurulum adımları `README.md` dosyasına işlendi ve GitHub reposuna pushlandı.

### 💡 Öğrenilenler ve Teknik Deneyimler
- **JDK Sürüm Uyumluluğu:** Spring Boot 3.x mimarisinin en az Java 17 gerektirdiği ve Maven derleme hatalarında (`release version 17 not supported`) ortam değişkenleri ve JDK path yönetiminin önemi deneyimlendi.
- **Monorepo Dizin Yönetimi:** Backend ve Frontend'in tek repo altında izole dizinlerde tutulurken Maven wrapper (`mvnw.cmd`) ve `pom.xml` hiyerarşisinin doğru yapılandırılması pekiştirildi.
- **CORS Yönetimi:** Tarayıcı güvenlik protokolleri gereği farklı portlarda çalışan Frontend (`5173`) ve Backend (`8080`) arasındaki `@CrossOrigin` yapılandırması uygulandı.

---

### 🎯 Yarınki Odak (Sıradaki Adım)
- PostgreSQL tablolarını oluşturacak JPA Entity modellerinin (`User`, `Role`, `Project`, `WeeklyReport`, `WorkItem`, `RiskIssue`) kodlanması.
- Repository ve Service katmanlarının kurularak ilk CRUD API'lerinin yazılması.