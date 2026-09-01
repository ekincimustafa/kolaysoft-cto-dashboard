# Kolaysoft - Haftalık Proje Durum Raporlama ve CTO Takip Sistemi

Kolaysoft bünyesindeki projelerin haftalık ilerleme, takvim, risk ve iş kalemlerinin dijital ortamda toplanmasını ve CTO seviyesinde tek bir gösterge panelinden (dashboard) izlenmesini sağlayan kurumsal yönetim platformu.

---

## 🛠️ Teknoloji Yığını

- **Backend:** Java 17, Spring Boot 3.x (Spring Web, Spring Data JPA, Hibernate, Bean Validation, Lombok)
- **Frontend:** React 18, Vite, JavaScript, ESLint
- **Veritabanı:** PostgreSQL 16 (Docker Konteyner)
- **Konteynerizasyon:** Docker & Docker Compose
- **Versiyon Kontrolü:** Git & GitHub (Monorepo yapısı)

---

## 🚀 Projeyi Yerelde Çalıştırma

### 1. Veritabanını Başlatma (Docker)
Ana dizinde PostgreSQL servisini başlatın:

    docker compose up -d

### 2. Backend Servisini Başlatma

    cd backend
    ./mvnw spring-boot:run
    # Windows PowerShell için: .\mvnw.cmd spring-boot:run

- **API Varsayılan Port:** http://localhost:8080
- **Sağlık Kontrolü (Health Check):** http://localhost:8080/api/health

### 3. Frontend Uygulamasını Başlatma

    cd frontend
    npm install
    npm run dev

- **Arayüz Varsayılan Port:** http://localhost:5173

---

## 📂 Dokümantasyon
- [Ön Analiz ve Gereksinim Dokümanı (analiz.md)](./analiz.md)
- [Teknik Karar Notu ve Mimari Tercihler (kararlar.md)](./kararlar.md)