# Mimari Karar Kayıtları (Architecture Decision Records - ADR)

**Proje:** Haftalık Proje Durum Raporlama ve CTO Takip Sistemi  
**Son Güncelleme:** 2 Eylül 2026  
**Durum:** Kabul Edildi (Accepted)  

---

## ADR 01: Monorepo Dizin Yapısı Tercihi
- **Bağlam:** Projede Spring Boot backend ve React frontend olmak üzere iki bağımsız bileşen bulunmaktadır.
- **Karar:** İki ayrı Git repository'si açmak yerine, kök dizinde `/backend` ve `/frontend` klasörlerini içeren tek bir Git repository (Monorepo) yapısı kullanılmasına karar verilmiştir.
- **Gerekçe:** Tek bir commit geçmişiyle uçtan uca özellik takibi yapabilmek, dokümantasyonu (`analiz.md`, `README.md`) merkezi tutmak ve staj değerlendirme sürecinde repoyu tek noktadan yönetebilmek.

---

## ADR 02: Teknoloji Yığını ve İskelet Standartları
- **Bağlam:** Kurumsal gereksinimleri karşılayan, sürdürülebilir ve endüstri standardı bir teknoloji omurgası kurulması gerekmektedir.
- **Karar:** 
  - **Backend:** Java 17 + Spring Boot 3.x (Spring Data JPA, Hibernate, Bean Validation, Lombok).
  - **Frontend:** React 18 + Vite (Hızlı HMR, modern ES modül desteği).
  - **Veritabanı:** PostgreSQL 16 (Docker Compose üzerinde izole konteyner).
- **Gerekçe:** Kurumsal backend projelerinde Spring Boot ve PostgreSQL uyumu; frontend tarafında Vite'ın geliştirme hızı ve hafifliği.

---

## ADR 03: Rol Kapsamı ve Yetki Matrisi
- **Bağlam:** Analiz aşamasında farklı rol alternatifleri (Ekip Lideri vb.) gündeme gelmiştir.
- **Karar:** MVP (ilk 20 gün) kapsamında roller yalnızca **Admin**, **Proje Yöneticisi (PY)** ve **CTO** ile sınırlandırılmıştır.
- **Gerekçe:** Proje Sorumlusu Muhammet Fatih Diker onayıyla, 20 günlük geliştirme takviminde odak kaybını önlemek ve temel değer üreten raporlama zincirini öncelikle ayağa kaldırmak.

---

## ADR 04: Aggregate Root ve Cascade Yaşam Döngüsü
- **Bağlam:** Haftalık raporlara bağlı iş kalemleri (`WorkItem`) ve risk kayıtlarının (`RiskIssue`) veritabanı yaşam döngüsü belirlenmelidir.
- **Karar:** `WeeklyReport` varlığı bir Aggregate Root olarak kabul edilmiştir. `WorkItem` ve `RiskIssue` ilişkileri `CascadeType.ALL` ve `orphanRemoval = true` yapılandırması ile ana rapora bağlanacaktır.
- **Gerekçe:** İş kalemleri veya risk kayıtları bağımsız bir varlık değildir; doğrudan ait oldukları haftalık raporla var olur veya silinir. Bu yaklaşım veritabanında sahipsiz kayıt (orphan record) kalmasını önler ve tek işlemde (transaction) kaydedilmesini sağlar.

---

## ADR 05: Veri İzolasyonu ve Güvenlik Filtreleme Seviyesi
- **Bağlam:** Proje Yöneticilerinin yalnızca sorumlu oldukları projeleri ve raporları görmesi gerekmektedir.
- **Karar:** Veri izolasyonu yalnızca arayüzde gizleme şeklinde değil; backend katmanında Repository sorguları seviyesinde (`WHERE manager_id = :currentUserId`) uygulanacaktır.
- **Gerekçe:** Doğrudan ID tahminiyle (Insecure Direct Object References - IDOR) başka bir projenin verisine veya raporuna erişilmesini kesin olarak engellemek.

---

## ADR 06: Tip Güvenliği, Durum Yönetimi ve Validasyon
- **Bağlam:** Proje durumları, risk seviyeleri ve takvim durumlarının veri tutarlılığı sağlanmalıdır.
- **Karar:**
  - Tüm durum ve rol alanları için serbest metin yerine Java Enum (`RoleType`, `ProjectStatus`, `ScheduleStatus`, `RiskLevel`) yapıları kullanılacaktır.
  - Veri doğrulama kuralları (0-100 ilerleme aralığı, gelecek tarih kısıtlaması, zorunlu metin uzunlukları) hem DTO katmanında Jakarta Bean Validation anotasyonlarıyla hem de Service katmanında kontrol edilecektir.
- **Gerekçe:** Veritabanında geçersiz veya hatalı yazılmış veri oluşmasını engellemek, API seviyesinde standart HTTP 400 Bad Request yanıtları dönebilmek.