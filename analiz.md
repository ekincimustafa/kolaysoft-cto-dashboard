# Haftalık Proje Durum Raporlama ve CTO Takip Sistemi
## Ön Analiz ve Gereksinim Dokümanı (v1.0)

**Tarih:** 1 Eylül 2026  
**Geliştirici:** Mustafa Ekinci  
**Geliştirme Yönü:** Full Stack  
**Sorumlu Mühendis:** Muhammet Fatih Diker (Kapsam ve Kural Onayı alındı)
---

### 1. Proje Amacı
Kolaysoft bünyesinde farklı proje yöneticileri tarafından yürütülen projelerin (PEYK, EczacıPOS, e-Dönüşüm vb.) haftalık durum raporlarının standart bir dijital platformda toplanması; Proje Yöneticilerinin kendi projelerini düzenli raporlayabilmesi ve CTO'nun tüm portföyü tek bir dashboard üzerinden izleyip filtreleyerek stratejik kararlar alabilmesini sağlamaktır.

### 2. Problem ve Mevcut Durum
Şu anda haftalık durum paylaşımları PowerPoint sunumları, e-postalar ve toplantı notları üzerinden dağınık bir şekilde yürütülmektedir. Bu durum:
- Farklı kişilerin farklı formatlar kullanmasına,
- Aynı bilginin tekrar tekrar hazırlanmasına,
- Projeler arası ilerleme ve risk karşılaştırmasının zorlaşmasına,
- Geciken ve bloke olan işlerin CTO/yönetim tarafından geç fark edilmesine,
- Geçmiş haftalarda alınan kararların izinin sürülememesine neden olmaktadır.

---

### 3. Kapsam (20 Günlük MVP)
İlk 20 günlük MVP (Minimum Viable Product) sürümünde uçtan uca çalışır durumda teslim edilecek temel modüller:
- **Kullanıcı ve Rol Yönetimi:** Admin, Proje Yöneticisi ve CTO rolleri.
- **Proje Yönetimi:** Proje tanımlama, listeleme ve proje yöneticisi atama.
- **Haftalık Rapor Modülü:** Hedeflenen/gerçekleşen ilerleme (%), genel durum, takvim, risk seviyesi, canlı task sayısı, yapılanlar, yapılacaklar ve genel not alanlarının kaydedilmesi.
- **İş Kalemleri (Work Items):** Rapora bağlı alt görevlerin başlık, sorumlu, durum ve tarih bilgileriyle girilmesi.
- **CTO Dashboard:** Tüm projelerin son durumlarının özet sayaçlar, sağlık/durum renkleri ve temel filtrelerle tek ekranda gösterimi.
- **Filtreleme:** Proje, hafta/tarih, durum ve risk seviyesine göre temel filtreler.
- **Validasyon ve Hata Yönetimi:** Zorunlu alan kontrolleri, sayısal/yüzde sınırları ve anlamlı hata mesajları.

### 4. Kapsam Dışı (21-60. Günler / Genişletme)
Aşağıdaki özellikler MVP fazında geliştirilmeyecek, sonraki aşamalara bırakılacaktır:
- Jira / Azure DevOps / Kurum içi ERP entegrasyonları
- Otomatik e-posta ve sistem içi bildirim mekanizmaları
- PDF / Excel dışa aktarma (export) özellikleri
- Yapay zekâ (AI) destekli otomatik rapor özeti çıkarma
- Detaylı kullanıcı işlem geçmişi (Audit Log)

---

### 5. Roller ve Yetkiler
| Rol | Tanım ve Yetki Sınırı |
| :--- | :--- |
| **Admin** | Kullanıcıları, rolleri ve projeleri sisteme tanımlar; proje yöneticilerini projelere atar. |
| **Proje Yöneticisi (PY)** | Yalnızca kendisine atanmış projeleri listeler, bu projeler için haftalık rapor ve iş kalemi oluşturur/günceller. |
| **CTO** | Sistemdeki tüm projeleri ve haftalık rapor geçmişlerini görüntüler; dashboard üzerinde filtreleme ve portföy analizi yapar. (Salt okunur / Read-only) |

---

### 6. Kullanıcı Hikâyeleri
- **US-01 (Giriş):** Bir kullanıcı olarak, sisteme e-posta ve şifremle giriş yapmak istiyorum; böylece rolüme uygun ekranlara erişebilirim.
- **US-02 (Proje Listesi):** Bir Proje Yöneticisi olarak, sorumlu olduğum projeleri listelemek istiyorum; böylece hangi projelere rapor gireceğimi seçebilirim.
- **US-03 (Rapor Oluşturma):** Bir Proje Yöneticisi olarak, seçtiğim proje için ilgili haftanın ilerleme yüzdesini, yapılanlarını, yapılacaklarını ve risklerini girmek istiyorum; böylece haftalık durum paylaşımını tamamlayabilirim.
- **US-04 (İş Kalemi Ekleme):** Bir Proje Yöneticisi olarak, haftalık rapora bağlı spesifik alt görevleri (WorkItem) durumu ve sorumlusuyla eklemek istiyorum; böylece operasyonel detayları netleştirebilirim.
- **US-05 (CTO Dashboard):** Bir CTO olarak, tüm şirket projelerinin güncel sağlık durumunu, ilerleme yüzdelerini ve risk seviyelerini tek ekranda görmek istiyorum; böylece portföy seviyesinde hızlı aksiyon alabilirim.
- **US-06 (Filtreleme):** Bir CTO olarak, projeleri durumlarına (Örn: Gecikti, Bloke, Riskli) veya haftalara göre filtrelemek istiyorum; böylece problemli alanlara doğrudan odaklanabilirim.

---

### 7. Ekran Listesi
1. **Giriş Ekranı :** E-posta ve şifre giriş formu, hata geri bildirimleri.
2. **Proje Listesi Ekranı:** Proje kartları/tablosu ve yeni rapor ekleme butonları.
3. **Haftalık Rapor Form Ekranı:** Rapor haftası, hedeflenen/gerçekleşen %, durum, takvim, risk, yapılanlar, yapılacaklar ve not giriş formları.
4. **İş Kalemi Yönetim Ekranı / Bileşeni:** Görev ekleme, düzenleme ve durum (Planlandı, Devam Ediyor, Tamamlandı vb.) güncelleme alanı.
5. **CTO Dashboard Ekranı:** Portföy sayaçları (Toplam proje, riskli proje vb.), proje sağlık tablosu, filtre paneli ve proje detayına geçiş bağlantıları.
6. **Proje Detay Ekranı:** Geçmiş haftalık raporların ve iş kalemlerinin zaman çizelgesi olarak incelenebildiği detay ekranı.

---

### 8. Veri Modeli ve API Taslağı

#### Temel Veri Varlıkları
- **User:** `id`, `email`, `password_hash`, `first_name`, `last_name`, `role_id`, `created_at`
- **Role:** `id`, `name` (ADMIN, PROJECT_MANAGER, CTO)
- **Project:** `id`, `name`, `client_name`, `status`, `manager_id`, `created_at`
- **WeeklyReport:** `id`, `project_id`, `report_date`, `week_number`, `year`, `target_progress`, `actual_progress`, `status`, `schedule_status`, `risk_level`, `active_task_count`, `summary_note`, `created_at`
- **WorkItem:** `id`, `report_id`, `title`, `description`, `assignee_name`, `status`, `planned_date`, `completed_date`
- **RiskIssue:** `id`, `report_id`, `title`, `impact_level`, `action_plan`, `owner_name`, `status`

#### Temel API Endpoint'leri
- `POST /api/auth/login` : Kullanıcı doğrulama ve oturum açma
- `GET /api/projects` : Giriş yapan kullanıcıya göre projeleri listeleme
- `POST /api/projects` : Yeni proje oluşturma (Admin)
- `POST /api/reports` : Yeni haftalık rapor oluşturma
- `GET /api/reports/{id}` : Rapor detayı ve iş kalemlerini getirme
- `GET /api/projects/{id}/reports` : Bir projenin geçmiş raporlarını listeleme
- `POST /api/work-items` : Rapora yeni iş kalemi ekleme
- `GET /api/dashboard/summary` : CTO Dashboard için tüm portföy özet verisi ve sayaçlar

---

### 9. İş Kuralları ve Doğrulamalar
1. **Yüzde Doğrulaması:** Hedeflenen ve gerçekleşen ilerleme değerleri 0 ile 100 arasında bir tamsayı olmak zorundadır.
2. **Proje Sahipliği:** Proje Yöneticisi yalnızca `manager_id` değeri kendi kullanıcı ID'si ile eşleşen projeler için rapor oluşturabilir veya güncelleyebilir.
3. **Zorunlu Alanlar:** Rapor haftası, hedeflenen %, gerçekleşen %, durum, risk seviyesi ve en az bir "yapılanlar" açıklaması zorunludur.
4. **Haftalık Tek Rapor:** Aynı proje için aynı yıl ve hafta numarasına ait birden fazla aktif rapor açılamaz.

---

### 10. Negatif ve İstisna Durumlar
- **401 Unauthorized:** Geçersiz oturum bilgisi veya süresi dolmuş token ile istek atılması.
- **403 Forbidden:** Proje Yöneticisinin başka bir yöneticinin projesine rapor kaydetmeye çalışması.
- **404 Not Found:** Sistemde bulunmayan bir proje veya rapor ID'si talep edildiğinde uygun hata mesajının dönülmesi.
- **400 Bad Request:** Zorunlu form alanlarının boş bırakılması veya yüzde değerlerinin 0-100 dışına çıkması durumunda kullanıcıya anlaşılır validasyon hatalarının gösterilmesi.

---

### 11. Kabul Kriterleri
- [ ] Bir Proje Yöneticisi sisteme giriş yaptığında yalnızca kendi sorumlu olduğu projeleri görebilmelidir.
- [ ] Proje Yöneticisi haftalık rapor formunu eksiksiz doldurup kaydettiğinde, veritabanına ilişkili iş kalemleriyle birlikte kayıt atılmalıdır.
- [ ] CTO rolündeki kullanıcı sisteme girdiğinde tüm projelerin son haftalık durumlarını ve risk renklerini dashboard'da görebilmelidir.
- [ ] Dashboard üzerindeki durum/risk filtreleri tıklandığında tablo ilgili projeleri anlık olarak filtreleyebilmelidir.
- [ ] API seviyesinde geçersiz veriler (örn: %150 ilerleme) ve yetkisiz istekler reddedilmeli, uygun HTTP durum kodları ve mesajları dönmelidir.

---

### 12. Kesinleşen Kapsam ve Mimari Kararları (Sorumlu Mühendis Onaylı)
*Aşağıdaki kararlar Proje Sorumlusu Muhammet Fatih Diker ile teyit edilerek kesinleştirilmiştir:*
1. **Proje Görünürlüğü:** Proje Yöneticileri yalnızca kendilerine atanan projeleri listeleyebilir ve yönetebilir. Portföy genelini yalnızca CTO rolü görür.
2. **İlerleme Hesabı:** İlerleme yüzdeleri (% Hedeflenen / % Gerçekleşen) form üzerinden Proje Yöneticisi tarafından sayısal olarak girilir.
3. **Rol Kapsamı:** MVP (ilk 20 gün) sürecinde odak kaybını önlemek adına "Ekip Lideri" rolü kapsam dışı bırakılmıştır. Sistem Admin, Proje Yöneticisi ve CTO rolleriyle geliştirilecektir.
4. **Geçmiş Rapor Kilitlenmesi:** Geçmiş haftaların raporları geriye dönük veri tutarlılığı ve denetim güvenliği için düzenlemeye kapatılır, yalnızca görüntülenebilir.