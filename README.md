#  Aplikasi Manajemen Kontak

**Berbasis Java Swing & Penyimpanan CSV**

##  Deskripsi Proyek

Aplikasi Manajemen Kontak adalah aplikasi desktop berbasis **Java Swing** yang digunakan untuk mengelola data kontak secara sederhana dan terstruktur.
Aplikasi ini mendukung fitur **login**, **CRUD data kontak**, **pencarian**, **pengurutan**, **penyimpanan permanen ke file CSV**, serta **laporan ringkasan data**.

Aplikasi ini dibuat untuk memenuhi kebutuhan pembelajaran pemrograman Java GUI serta implementasi konsep:

* OOP (Object Oriented Programming)
* CRUD
* File handling
* Exception handling
* Collection (ArrayList, Map)
* GUI berbasis Swing

---

##  Struktur Package

```
org.example
│
├── App.java
│
├── model
│   └── Contact.java
│
├── util
│   └── FileManager.java
│
└── view
    ├── LoginFrame.java
    ├── DashboardFrame.java
    ├── ContactListFrame.java
    ├── InputContactFrame.java
    └── ReportFrame.java
```

---

##  Teknologi yang Digunakan

* **Bahasa** : Java
* **GUI** : Java Swing
* **Penyimpanan Data** : File CSV
* **Library** :

    * `java.swing`
    * `java.time`
    * `java.io`
    * `java.nio`
    * `java.util`

---

##  Fitur Login

* Username dan password bersifat **hardcoded**

```
Username : admin
Password : admin123
```

* Menggunakan **percabangan if-else**
* Jika login berhasil → masuk ke Dashboard
* Jika gagal → tampil pesan error

File terkait:

* `LoginFrame.java`

---

##  Dashboard

Dashboard berfungsi sebagai pusat navigasi aplikasi.

### Fitur Dashboard:

* Menampilkan **total jumlah kontak**
* Menampilkan **aktivitas kontak terbaru**
* Navigasi ke:

    * Data Kontak
    * Tambah Kontak
    * Laporan
    * Keluar Aplikasi

File terkait:

* `DashboardFrame.java`

---

##  Manajemen Data Kontak (CRUD)

### 1. Create (Tambah Kontak)

* Input:

    * Nama
    * Nomor Telepon (12 digit, hanya angka)
    * Kategori (Keluarga, Teman, Kerja, Lainnya)
* Tanggal otomatis menggunakan `LocalDate.now()`
* Validasi input menggunakan exception

File terkait:

* `InputContactFrame.java`

---

### 2️. Read (Lihat Data Kontak)

* Data ditampilkan dalam bentuk **JTable**
* Kolom:

    * Nama
    * Nomor Telepon
    * Kategori
    * Tanggal

File terkait:

* `ContactListFrame.java`

---

### 3️. Update (Edit Kontak)

* Edit data kontak yang dipilih dari tabel
* Data lama dimuat otomatis ke form
* Tanggal kontak **tetap dipertahankan**

File terkait:

* `ContactListFrame.java`
* `InputContactFrame.java`

---

### 4️. Delete (Hapus Kontak)

* Menghapus kontak terpilih
* Konfirmasi sebelum penghapusan
* Data langsung diperbarui dan disimpan

File terkait:

* `ContactListFrame.java`

---

##  Pencarian & Sorting

* **Pencarian** berdasarkan:

    * Nama
    * Nomor Telepon
    * Kategori
* **Sorting** data berdasarkan nama (A–Z)
* Menggunakan `Comparator`

File terkait:

* `ContactListFrame.java`

---

##  Penyimpanan Data

* Data disimpan secara permanen dalam file:

```
data_kontak.csv
```

* Format CSV:

```
nama,nomorTelepon,kategori,tanggal
```

### Mekanisme:

* `toCSV()` → menyimpan objek ke CSV
* `fromCSV()` → membaca data dari CSV

File terkait:

* `Contact.java`
* `FileManager.java`

---

##  Laporan Data Kontak

Menampilkan laporan ringkasan dalam bentuk teks, meliputi:

* Total jumlah kontak
* Jumlah dan persentase kontak per kategori
* Kontak terbaru
* Rata-rata panjang nama
* Tanggal pembuatan laporan

File terkait:

* `ReportFrame.java`

---

##  Model Data (OOP)

### Class `Contact`

Atribut:

* `nama`
* `nomorTelepon`
* `kategori`
* `tanggal`

Method penting:

* Getter & Setter
* `getTanggalFormatted()`
* `toCSV()`
* `fromCSV()`

File terkait:

* `Contact.java`

---

##  Cara Menjalankan Aplikasi

1. Pastikan Java sudah terinstall
2. Buka project di IDE (IntelliJ / NetBeans / VS Code)
3. Jalankan file:

```
App.java
```

4. Login menggunakan akun default
5. Gunakan aplikasi melalui Dashboard

---

##  Kesimpulan

Aplikasi Manajemen Kontak ini telah mengimplementasikan:

* GUI Java Swing
* Konsep OOP
* CRUD lengkap
* File handling (CSV)
* Exception handling
* Validasi input
* Navigasi antar frame
