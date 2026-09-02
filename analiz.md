# Haftalık Proje Durum Raporlama ve CTO Takip Sistemi
## Ön Analiz ve Gereksinim Dokümanı (v1.1)

**Tarih:** 2 Eylül 2026  
**Geliştirici:** Mustafa Ekinci  
**Geliştirme Yönü:** Full Stack  
**Sorumlu Mühendis:** Muhammet Fatih Diker (Kapsam ve Kural Onayı Alındı)  
**Analiz Mentoru:** Ayşenur Yaylacı (Geri Bildirimler İşlendi)  

---

### 1. Proje Amacı
Kolaysoft bünyesinde farklı proje yöneticileri tarafından yürütülen projelerin (PEYK, EczacıPOS, e-Dönüşüm vb.) haftalık durum raporlarının standart bir dijital platformda toplanması; Proje Yöneticilerinin kendi projelerini düzenli raporlayabilmesi ve CTO'nun tüm portföyü tek bir dashboard üzerinden izleyip filtreleyerek stratejik kararlar alabilmesini sağlamaktır.

### 2. Problem ve Mevcut Durum
Haftalık durum paylaşımları PowerPoint sunumları, e-postalar ve toplantı notları üzerinden dağınık yürütülmektedir. Bu durum:
- Farklı yöneticilerin farklı formatlar kullanmasına,
- Aynı bilginin tekrar tekrar hazırlanmasına,
- Projeler arası ilerleme ve risk karşılaştırmasının zorlaşmasına,
- Geciken ve bloke olan işlerin CTO ve yönetim tarafından geç fark edilmesine,
- Geçmiş haftalarda alınan kararların izinin sürülememesine neden olmaktadır.

---

### 3. Kapsam (20 Günlük MVP)
İlk 20 günlük MVP sürümünde teslim edilecek modüller:
- **Kullanıcı ve Rol Yönetimi:** Admin, Proje Yöneticisi ve CTO rolleri.
- **Proje Yönetimi:** Proje tanımlama, listeleme ve yönetici atama.
- **Haftalık Rapor Modülü:** Hedeflenen/gerçekleşen ilerleme (%), durum, takvim, risk, canlı task sayısı, yapılanlar, yapılacaklar ve genel not alanları.
- **İş Kalemleri (Work Items) ve Risk Kayıtları:** Rapor bazlı alt görev ve risk/engel girişleri.
- **CTO Dashboard:** Portföy sayaçları, sağlık/durum renkleri ve birleşik (AND) filtreleme paneli.
- **Validasyon ve Hata Yönetimi:** Form seviyesinde alan kısıtları ve standart HTTP hata yanıtları.

### 4. Kapsam Dışı (21-60. Günler / Genişletme)
- Dış sistem entegrasyonları (Jira, Azure DevOps, Kurum içi ERP).
- E-posta ve sistem içi bildirim mekanizmaları.
- PDF / Excel dışa aktarma motoru.
- Yapay zekâ (AI) destekli otomatik rapor özeti.
- Detaylı kullanıcı işlem loglaması (Audit Log).

---

### 5. Roller ve Yetkiler
- **Admin:** Kullanıcı, rol ve proje tanımlamalarını yapar; Proje Yöneticilerini projelere atar.
- **Proje Yöneticisi (PY):** Yalnızca kendisine atanmış projeleri listeler; bu projeler için haftalık rapor ve alt kalemleri girer/günceller.
- **CTO:** Sistemdeki tüm projeleri ve haftalık rapor geçmişini görüntüler; dashboard üzerinde filtreleme ve portföy analizi yapar (Salt okunur).

---

### 6. Kullanıcı Hikâyeleri (3 Katmanlı Standart Yapı)

#### US-01: Sisteme Giriş ve Kimlik Doğrulama
- **Hikâye:** Bir sistem kullanıcısı olarak, e-posta ve şifremle giriş yapmak istiyorum; böylece rolüme uygun ekranlara güvenli bir şekilde erişebilirim.
- **İş Kuralları:**
  1. E-posta adresi geçerli formatta olmalı ve sistemde kayıtlı bulunmalıdır.
  2. Şifre en az 6 karakter olmalıdır.
  3. Başarılı girişte kullanıcı rolüne göre yönlendirme yapılır (CTO -> Dashboard, PY -> Proje Listesi).
- **Kabul Kriterleri:**
  - Given kullanıcı giriş ekranındadır, When geçerli e-posta ve şifre girip giriş butonuna tıklar, Then JWT oturumu oluşturulur ve rolüne ait ana ekrana yönlendirilir.
  - Given kullanıcı geçersiz şifre girer, When giriş butonuna tıklar, Then "E-posta veya şifre hatalı" uyarısı gösterilir (HTTP 401).

#### US-02: Proje Listesini İnceleme ve Erişim İzolasyonu
- **Hikâye:** Bir Proje Yöneticisi olarak, yalnızca sorumlu olduğum projeleri listelemek istiyorum; böylece yetki sınırlarım içindeki projelere odaklanabilirim.
- **İş Kuralları:**
  1. Proje Yöneticisi sadece atanmış olduğu projeleri görebilir; diğer yöneticilerin projelerini listeleyemez.
  2. Admin ve CTO tüm projeleri listeleyebilir.
- **Kabul Kriterleri:**
  - Given Proje Yöneticisi sisteme giriş yapmıştır, When proje listesi sayfası açılır, Then yalnızca yöneticisi olduğu projeler kart veya tablo halinde listelenir.
  - Given Proje Yöneticisi yetkisi olmayan bir projenin ID'sine URL üzerinden doğrudan erişmeye çalışır, When sayfa yüklenir, Then "Erişim Yetkiniz Yok" hatası gösterilir (HTTP 403).

#### US-03: Haftalık Rapor Oluşturma ve Düzenleme
- **Hikâye:** Bir Proje Yöneticisi olarak, seçtiğim proje için ilgili haftanın ilerleme, takvim ve durum verilerini girmek istiyorum; böylece haftalık durum paylaşımını tamamlayabilirim.
- **İş Kuralları:**
  1. Bir proje için aynı yıl ve hafta numarasına ait yalnızca bir aktif rapor oluşturulabilir.
  2. İlerleme yüzdeleri (% Hedeflenen / % Gerçekleşen) 0 ile 100 arasında tamsayı olmalıdır.
  3. Rapor tarihi için gelecek haftalar seçilemez; yalnızca mevcut hafta veya geriye dönük eksik haftalar raporlanabilir.
  4. Geçmiş haftalara ait kilitlenmiş raporlar düzenlenemez, salt okunur görüntülenir.
- **Kabul Kriterleri:**
  - Given PY sorumlu olduğu projenin rapor formundadır, When geçerli hafta, yüzdeler (0-100), durumlar ve metin alanlarını doldurup kaydeder, Then rapor veritabanına işlenir ve onay mesajı gösterilir.
  - Given PY gerçekleşen ilerleme değerine 120 yazar, When kaydet butonuna basar, Then form hata verir ve kaydetme engellenir.

#### US-04: İş Kalemi (WorkItem) ve Risk/Engel Yönetimi
- **Hikâye:** Bir Proje Yöneticisi olarak, haftalık rapora bağlı spesifik alt görevleri ve engelleri eklemek istiyorum; böylece operasyonel detayları şeffaf hâle getirebilirim.
- **İş Kuralları:**
  1. İş kalemleri ve riskler doğrudan ilgili haftalık rapora bağlıdır.
  2. Bir raporda en az bir iş kalemi girilmesi önerilir.
  3. Risk kaydında etki seviyesi (Düşük/Orta/Yüksek) ve aksiyon planı zorunludur.
- **Kabul Kriterleri:**
  - Given rapor oluşturma ekranı açıktır, When PY yeni iş kalemi başlığı ve sorumlusu girip ekler, Then iş kalemi anlık olarak alt listeye eklenir.
  - Given bir risk kaydı eklenmektedir, When aksiyon planı boş bırakılır, Then sistem validasyon uyarısı vererek kaydı durdurur.

#### US-05: CTO Portföy Dashboard İzleme
- **Hikâye:** Bir CTO olarak, şirketteki tüm projelerin en güncel sağlık, ilerleme ve risk durumlarını tek ekrandan izlemek istiyorum; böylece portföy seviyesinde hızlı aksiyon alabilirim.
- **İş Kuralları:**
  1. CTO rolü yalnızca okuma yetkisine sahiptir; rapor veya proje üzerinde düzenleme yapamaz.
  2. Dashboard varsayılan olarak sistemdeki tüm projelerin en son haftalık raporunu gösterir.
  3. Sayfanın üstünde toplam proje, riskli proje ve geciken proje sayaçları yer alır.
- **Kabul Kriterleri:**
  - Given CTO sisteme giriş yapmıştır, When dashboard yüklenir, Then tüm projelerin son haftalık ilerleme çubukları, durum etiketleri ve risk seviyeleri tek bir tabloda listelenir.
  - Given CTO bir projeye tıklar, When detay sayfasına geçer, Then projenin geçmiş haftalık rapor zaman çizelgesini görüntüler.

#### US-06: Dashboard ve Portföy Filtreleme
- **Hikâye:** Bir CTO olarak, projeleri durum, risk ve haftalara göre filtrelemek istiyorum; böylece darboğaz yaşanan projelere odaklanabilirim.
- **İş Kuralları:**
  1. Filtreler birleşik (AND mantığıyla) çalışır. Örneğin: "Risk = Yüksek" VE "Takvim = Gecikmeli".
  2. Filtreler temizlendiğinde tablo orijinal haline döner.
- **Kabul Kriterleri:**
  - Given CTO dashboard üzerindedir, When risk filtresinden "Yüksek" değerini seçer, Then ekrandaki proje listesi anlık olarak yalnızca yüksek riskli projelere daraltılır.
  - Given hiçbir proje seçilen filtre kriterine uymamaktadır, When filtre uygulanır, Then "Kriterlere uygun proje bulunamadı" bilgisi gösterilir.

---

### 7. Ekran Detayları ve Alan Kısıtları

#### Ekran 1: Giriş Ekranı (Login)
- **Erişebilen Roller:** Tüm Roller
- **Alan Listesi:**
  - E-posta: Metin alanı, Zorunlu, E-posta formatında olmalı.
  - Şifre: Şifre alanı, Zorunlu, Min 6 karakter.
  - Giriş Yap Butonu: Tıklama aksiyonu.

#### Ekran 2: Proje Listesi Ekranı
- **Erişebilen Roller:** Admin, Proje Yöneticisi, CTO
- **Alan Listesi:**
  - Proje Kartı / Satırı: Proje adı, Müşteri adı, Atanan Proje Yöneticisi, Güncel Durum.
  - Yeni Rapor Ekle Butonu: Yalnızca Proje Yöneticisi için görünür, ilgili projenin rapor formunu açar.
  - Detay İncele Butonu: Projenin geçmiş rapor sayfasına yönlendirir.

#### Ekran 3: Haftalık Rapor Oluşturma ve Düzenleme Ekranı
- **Erişebilen Roller:** Proje Yöneticisi (Yalnızca kendi projeleri için)
- **Alan Listesi:**
  - Proje: Dropdown, Zorunlu, Yalnızca giriş yapan PY'ye atanmış projeler listelenir.
  - Hafta / Tarih: Date picker, Zorunlu, Gün/Ay/Yıl formatı. Gelecek tarih seçilemez; cari hafta veya geçmiş eksik haftalar girilebilir.
  - Hedeflenen İlerleme (%): Sayısal alan, Zorunlu, 0-100 arası tamsayı.
  - Gerçekleşen İlerleme (%): Sayısal alan, Zorunlu, 0-100 arası tamsayı.
  - Genel Durum: Dropdown, Zorunlu, Değerler: Planlandı, Devam Ediyor, Testte, Tamamlandı, Bloke.
  - Takvim Durumu: Dropdown, Zorunlu, Değerler: Zamanında, Gecikmeli, Risk Altında.
  - Risk Seviyesi: Dropdown, Zorunlu, Değerler: Düşük, Orta, Yüksek, Kritik.
  - Canlı Task Sayısı: Sayısal alan, Zorunlu, Negatif olamaz (Min: 0). İlgili haftada canlı ortamda çalışan veya bekleyen operasyonel iş adedini ifade eder.
  - Yapılanlar (Done): Çok satırlı metin alanı, Zorunlu, Min 10 karakter.
  - Yapılacaklar (To-Do): Çok satırlı metin alanı, Zorunlu, Min 10 karakter.
  - Genel Notlar: Çok satırlı metin alanı, İsteğe bağlı.

#### Ekran 4: İş Kalemi ve Risk/Engel Yönetim Bileşeni (Form İçi Alt Modül)
- **Erişebilen Roller:** Proje Yöneticisi
- **İş Kalemi (WorkItem) Alanları:**
  - Başlık: Metin alanı, Zorunlu.
  - Sorumlu Kişi: Metin alanı, Zorunlu.
  - Durum: Dropdown, Zorunlu (Açık, Devam Ediyor, Tamamlandı, Engellendi).
  - Planlanan Bitiş Tarihi: Date picker, Zorunlu.
- **Risk / Engel (RiskIssue) Alanları:**
  - Risk Tanımı: Metin alanı, Zorunlu.
  - Etki Seviyesi: Dropdown, Zorunlu (Düşük, Orta, Yüksek).
  - Aksiyon Planı: Metin alanı, Zorunlu.
  - Sorumlu: Metin alanı, Zorunlu.

#### Ekran 5: CTO Dashboard Ekranı
- **Erişebilen Roller:** CTO, Admin (Görüntüleme)
- **Alan Listesi:**
  - Sayaç Kartları: Toplam Proje, Zamanında İlerleyenler, Gecikenler, Yüksek Riskli Projeler.
  - Filtre Çubuğu: Hafta filtresi (Dropdown), Durum filtresi (Dropdown), Risk filtresi (Dropdown). Filtreler AND mantığıyla çalışır.
  - Portföy Tablosu: Proje Adı, PM Adı, Hedeflenen %, Gerçekleşen %, Takvim Durumu, Risk Seviyesi, Canlı Task Sayısı, Detay Butonu.

#### Ekran 6: Proje Detay ve Geçmiş Raporlar Ekranı
- **Erişebilen Roller:** CTO, Proje Yöneticisi, Admin
- **Alan Listesi:**
  - Proje Başlık Bilgileri: Ad, Müşteri, Yönetici.
  - Zaman Çizelgesi (Timeline): Projenin haftalık bazda geriye dönük tüm rapor kartları.
  - Rapor İnceleme Modalı: Seçilen geçmiş haftanın yapılan/yapılacak işleri, iş kalemleri ve risk kayıtları (Salt okunur).

---

### 8. Teknik Veri Modeli ve Varlık İlişkileri (Cardinality)

#### Varlık İlişkileri ve Kardinalite
- **Role (1) : (N) User** -> Bir rol birden çok kullanıcıya atanabilir; bir kullanıcının MVP kapsamında tek bir birincil rolü bulunur.
- **User [PM] (1) : (N) Project** -> Bir Proje Yöneticisi birden fazla projeden sorumlu olabilir; bir proje tek bir Proje Yöneticisine atanır.
- **Project (1) : (N) WeeklyReport** -> Bir proje zaman içinde birden fazla haftalık rapora sahip olur; her rapor tek bir projeye aittir.
- **WeeklyReport (1) : (N) WorkItem** -> Bir haftalık raporda birden fazla iş kalemi bulunur; bir iş kalemi tek bir rapora bağlıdır.
- **WeeklyReport (1) : (N) RiskIssue** -> Bir haftalık raporda birden fazla risk/engel kaydı bulunabilir; her kayıt tek bir rapora bağlıdır.

#### Tablo ve Alan Tanımları
- **users:** `id` (PK), `email`, `password_hash`, `first_name`, `last_name`, `role_id` (FK), `created_at`
- **roles:** `id` (PK), `name` (ADMIN, PROJECT_MANAGER, CTO)
- **projects:** `id` (PK), `name`, `client_name`, `status`, `manager_id` (FK -> users.id), `created_at`
- **weekly_reports:** `id` (PK), `project_id` (FK -> projects.id), `week_number`, `year`, `target_progress`, `actual_progress`, `status`, `schedule_status`, `risk_level`, `active_task_count`, `summary_done`, `summary_todo`, `notes`, `is_locked`, `created_at`
- **work_items:** `id` (PK), `report_id` (FK -> weekly_reports.id), `title`, `assignee_name`, `status`, `due_date`, `created_at`
- **risk_issues:** `id` (PK), `report_id` (FK -> weekly_reports.id), `title`, `impact_level`, `action_plan`, `owner_name`, `status`, `created_at`

#### Temel API Endpoint'leri
- `POST /api/auth/login` : Kimlik doğrulama ve JWT üretimi
- `GET /api/projects` : Giriş yapan kullanıcının yetkisine göre projeleri listeleme
- `POST /api/projects` : Yeni proje tanımlama (Admin)
- `GET /api/projects/{id}/reports` : Projeye ait haftalık rapor geçmişi
- `POST /api/reports` : Yeni haftalık rapor ve alt kalemlerini kaydetme
- `GET /api/reports/{id}` : Belirli bir raporun tüm detaylarını getirme
- `GET /api/dashboard/summary` : CTO Dashboard sayaç ve portföy listesi

---

### 9. İş Kuralları ve Doğrulamalar (Business Rules)
1. **İlerleme Değeri:** Hedeflenen ve gerçekleşen ilerleme alanları tamsayı (0-100) aralığında olmak zorundadır.
2. **Proje İzolasyonu:** Proje Yöneticileri yalnızca `manager_id` değeri kendi kullanıcı ID'leriyle eşleşen kayıtlar üzerinde işlem yapabilir.
3. **Tekillik Kuralı:** Aynı proje için aynı yıl ve hafta numarasıyla ikinci bir aktif rapor açılamaz (`project_id` + `year` + `week_number` benzersizdir).
4. **Tarih Geçerliliği:** Raporlama tarihi geleceğe yönelik açılamaz; yalnızca mevcut hafta veya geçmiş haftalar doldurulabilir.
5. **Rapor Kilitleme:** İçinde bulunulan haftanın raporlama süresi kapandıktan sonra geçmiş raporlar otomatik kilitlenir (`is_locked = true`), düzenlenemez.

---

### 10. Negatif ve İstisna Durumlar
- **400 Bad Request:** Form doğrulamalarının geçersiz olması (örn: %120 ilerleme, boş bırakılan zorunlu alanlar).
- **401 Unauthorized:** Oturum açılmamış veya token süresi dolmuş istekler.
- **403 Forbidden:** Proje Yöneticisinin yetkisi dışındaki bir projeyi okumaya veya rapor girmeye çalışması.
- **404 Not Found:** Sistemde bulunmayan proje veya rapor ID'sine erişim denemesi.
- **409 Conflict:** Aynı proje için aynı hafta numarasına mükerrer rapor oluşturulması girişimi.

---

### 11. Kesinleşen Kapsam Kararları (Mühendis Onaylı)
1. **Proje Görünürlüğü:** PY yalnızca kendi projelerini yönetir. CTO tüm portföyü izler.
2. **İlerleme Hesabı:** İlerleme yüzdeleri manuel girilir.
3. **Roller:** MVP sürümü Admin, Proje Yöneticisi ve CTO rolleriyle sınırlandırılmıştır.
4. **Kilitlenme:** Geçmiş raporlar veri bütünlüğü için salt okunur arşivlenir.