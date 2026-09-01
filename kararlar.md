# Teknik Karar Notları ve Mimari Tercihler (v1.0)

**Tarih:** 1 Eylül 2026  
**Geliştirici:** Mustafa Ekinci  
**Geliştirme Yönü:** Full Stack  

---

## 1. Mimari ve Repository Yapısı
Proje, Full Stack geliştirme yaklaşımıyla **Monorepo** (tek repository altında modüler klasörleme) olarak yönetilecektir. Bu yapı backend ve frontend entegrasyonunu kolaylaştırırken, tek bir Git geçmişi üzerinden tüm sistemin gelişimini izlemeyi sağlar.

Klasör Organizasyonu:
- backend/ : Java + Spring Boot REST API servisi
- frontend/ : React (Vite) Web uygulaması
- docker-compose.yml : PostgreSQL ve lokal ortam servisleri
- analiz.md : Ön analiz ve gereksinim dokümanı
- kararlar.md : Teknik kararlar ve mimari gerekçeleri
- README.md : Kurulum ve çalıştırma kılavuzu

---

## 2. Backend Tercihleri ve Katmanlı Mimari

### Teknoloji Seçimi
* **Dil & Sürüm:** Java 17 / 21 LTS
* **Framework:** Spring Boot 3.x
* **Veri Erişimi (ORM):** Spring Data JPA (Hibernate)
* **API Dokümantasyonu:** SpringDoc OpenAPI (Swagger UI)
* **Doğrulama:** Spring Boot Validation (Hibernate Validator)

### Seçim Gerekçeleri
1. **Kurumsal Standart ve Şirket Önerisi:** Kolaysoft staj yönetmeliğinde backend tarafı için Java + Spring Boot önerilmektedir. Kurumsal backend mimarilerini ve sektör standardı kalıpları deneyimlemek amacıyla tercih edilmiştir.
2. **Tip Güvenliği ve Sağlamlık:** Java'nın güçlü tip denetimi, iş kurallarının ve veri modellerinin çalışma zamanı öncesinde derleme aşamasında doğrulanmasını sağlar.
3. **Zengin Ekosistem:** Spring Boot; dahili bağımlılık enjeksiyonu (Dependency Injection), REST API desteği, katmanlı mimari araçları ve ilerleyen fazlarda entegre edilecek güvenlik (Spring Security) altyapısıyla projenin ihtiyaçlarını eksiksiz karşılar.

### Backend Katman Mimarisi
Backend kodu **Katmanlı Mimari (Layered Architecture)** prensiplerine uygun olarak şu katmanlara ayrılacaktır:
* **Controller Katmanı:** HTTP isteklerini (GET, POST, PUT, DELETE) karşılar, DTO doğrulamasını yapar ve HTTP yanıtlarını döner.
* **Service Katmanı:** İş kurallarını (business logic), hesaplamaları ve veri doğrulama mantığını yürütür.
* **Repository Katmanı:** Veritabanı ile Spring Data JPA üzerinden iletişim kurar.
* **Entity / Model:** Veritabanı tablolarının nesne karşılıklarıdır.
* **DTO (Data Transfer Object):** API istek ve yanıtlarında hassas/gereksiz verileri gizlemek ve veri sözleşmesini korumak için kullanılır.

---

## 3. Frontend Tercihleri ve Kullanıcı Deneyimi

### Teknoloji Seçimi
* **Kütüphane / Çatı:** React 18+ (Vite tabanlı kurulum)
* **Routing:** React Router DOM (v6+)
* **HTTP İstemcisi:** Axios / Fetch API
* **Stil / Tasarım:** Tailwind CSS (Modern, sade ve hızlı bileşen stillendirmesi için)
* **İkon Seti:** Lucide React / React Icons

### Seçim Gerekçeleri
1. **Bileşen Odaklı Yapı (Component-Based):** CTO Dashboard, Proje Listesi, Raporlama Formu ve Durum Kartları gibi ekranlar bağımsız, tekrar kullanılabilir bileşenler hâlinde modüler biçimde geliştirilebilir.
2. **Geliştirme Hızı:** Vite altyapısı, anlık modül yenileme (HMR) ve hızlı derleme süreleriyle geliştirme verimliliğini artırır.
3. **Şirket Standardı:** Frontend geliştirme yönü için yönetmelikte önerilen React ekosistemiyle tam uyumludur.

---

## 4. Veritabanı ve Altyapı Tercihleri

### Teknoloji Seçimi
* **Veritabanı:** PostgreSQL 16+
* **Konteynerizasyon:** Docker & Docker Compose

### Seçim Gerekçeleri
1. **İlişkisel Veri Bütünlüğü:** Kullanıcılar, Projeler, Haftalık Raporlar ve İş Kalemleri arasındaki bire-çok (1:N) ilişkileri ACID prensipleriyle güvenli bir şekilde saklar.
2. **Taşınabilirlik ve Kolay Kurulum:** `docker-compose.yml` dosyası sayesinde sistem, tek bir komutla izole bir PostgreSQL ortamında ayağa kaldırılabilir.

---

## 5. Öğrenme ve Uygulama Hedefleri (Öğrenme Planı)
* **Spring Boot Temelleri:** Anotasyon yapıları (`@RestController`, `@Service`, `@Repository`, `@Entity`), DTO dönüşümleri ve merkezi hata yönetimi (`@RestControllerAdvice`).
* **React State & Effect:** REST API entegrasyonu, yükleniyor/hata/boş durumlarının yönetimi ve form validasyonları.
* **API Sözleşmesi:** Frontend ile Backend arasında Swagger üzerinden tutarlı JSON veri akışının sağlanması.