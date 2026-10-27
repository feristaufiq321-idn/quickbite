package com.quickbite.quickbite.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "pembayaran")
public class Pembayaran {
    @Id
    @Column(name = "id_pembayaran", length = 36)
    private String idPembayaran;

    @Column(name = "jumlah_bayar", nullable = false, precision = 12, scale = 2)
    private BigDecimal jumlahBayar;

    @Column(nullable = false, length = 50)
    private String metode;

    @Column(name = "status_bayar", nullable = false, length = 30)
    private String statusBayar;

    @Column(name = "kode_referensi", length = 100)
    private String kodeReferensi;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_pesanan", nullable = false, unique = true)
    private Pesanan pesanan;

    protected Pembayaran() {}

    public Pembayaran(String idPembayaran, Pesanan pesanan,
                      BigDecimal jumlahBayar, String metode,
                      String statusBayar, String kodeReferensi) {
        this.idPembayaran = idPembayaran;
        this.pesanan = pesanan;
        this.jumlahBayar = jumlahBayar;
        this.metode = metode;
        this.statusBayar = statusBayar;
        this.kodeReferensi = kodeReferensi;
    }

    public boolean prosesVerifikasi() {
        return "SUCCESS".equalsIgnoreCase(statusBayar)
                && kodeReferensi != null && !kodeReferensi.isBlank();
    }

    public String getIdPembayaran() { return idPembayaran; }
    public BigDecimal getJumlahBayar() { return jumlahBayar; }
    public String getMetode() { return metode; }
    public String getStatusBayar() { return statusBayar; }
    public String getKodeReferensi() { return kodeReferensi; }
    public Pesanan getPesanan() { return pesanan; }
}