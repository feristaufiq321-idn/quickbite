# QuickBite

Aplikasi checkout dan pembayaran pemesanan makanan berbasis Java, Spring Boot, dan MariaDB.

## Fitur
- Checkout pesanan
- Perhitungan total harga
- Validasi ketersediaan stok
- Simulasi pembayaran menggunakan Mock Payment Gateway
- Penanganan kegagalan pembayaran
- Pembatalan pesanan dan pengembalian stok
- Pengelolaan data menggunakan Spring Data JPA

## Teknologi
- Java 21
- Spring Boot
- Spring Data JPA
- MariaDB
- Maven
- VS Code
- DBeaver

## Persiapan
1. Pasang Java 21 dan MariaDB.
2. Buat database bernama `quickbite`.
3. Atur variabel lingkungan database di PowerShell.

Contoh konfigurasi:

```powershell
$env:DB_URL = "jdbc:mariadb://localhost:3306/quickbite"
$env:DB_USERNAME = Read-Host "Username MariaDB"
$securePassword = Read-Host "Password MariaDB" -AsSecureString
$env:DB_PASSWORD = [System.Net.NetworkCredential]::new("", $securePassword).Password
```

Jangan menuliskan password asli ke dalam file proyek atau repository GitHub.

## Menjalankan Pengujian

```powershell
.\mvnw.cmd clean test
```

## Menjalankan Aplikasi

```powershell
.\mvnw.cmd spring-boot:run
```

## Pengujian HTTP
File `checkout-test.http` berisi contoh permintaan HTTP untuk membantu pengujian fitur checkout.

## Catatan
Pembayaran menggunakan simulasi Mock Payment Gateway, bukan gateway pembayaran sungguhan. Database dan data awal perlu disiapkan sesuai lingkungan masing-masing.
