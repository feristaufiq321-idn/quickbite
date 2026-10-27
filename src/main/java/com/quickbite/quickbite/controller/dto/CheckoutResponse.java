package com.quickbite.quickbite.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CheckoutResponse {
    private String idPesanan;
    private String idPembayaran;
    private BigDecimal totalBiaya;
    private String statusPesanan;
    private String statusPembayaran;
    private String kodeReferensi;
    private LocalDateTime tanggal;

    public CheckoutResponse(
            String idPesanan,
            String idPembayaran,
            BigDecimal totalBiaya,
            String statusPesanan,
            String statusPembayaran,
            String kodeReferensi,
            LocalDateTime tanggal) {
        this.idPesanan = idPesanan;
        this.idPembayaran = idPembayaran;
        this.totalBiaya = totalBiaya;
        this.statusPesanan = statusPesanan;
        this.statusPembayaran = statusPembayaran;
        this.kodeReferensi = kodeReferensi;
        this.tanggal = tanggal;
    }

    public String getIdPesanan() { return idPesanan; }
    public String getIdPembayaran() { return idPembayaran; }
    public BigDecimal getTotalBiaya() { return totalBiaya; }
    public String getStatusPesanan() { return statusPesanan; }
    public String getStatusPembayaran() { return statusPembayaran; }
    public String getKodeReferensi() { return kodeReferensi; }
    public LocalDateTime getTanggal() { return tanggal; }
}