package com.quickbite.quickbite.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "pesanan")
public class Pesanan {
    @Id
    @Column(name = "id_pesanan", length = 36)
    private String idPesanan;

    @Column(nullable = false)
    private LocalDateTime tanggal;

    @Column(name = "total_biaya", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalBiaya = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusPesanan status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_pelanggan", nullable = false)
    private Pelanggan pelanggan;

    @OneToMany(mappedBy = "pesanan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPesanan> items = new ArrayList<>();

    @OneToOne(mappedBy = "pesanan", cascade = CascadeType.ALL)
    private Pembayaran pembayaran;

    protected Pesanan() {}

    public Pesanan(String idPesanan, LocalDateTime tanggal, Pelanggan pelanggan) {
        this.idPesanan = idPesanan;
        this.tanggal = tanggal;
        this.pelanggan = pelanggan;
        this.status = StatusPesanan.MENUNGGU_BAYAR;
    }

    public void tambahItem(ItemPesanan item) {
        items.add(item);
        item.setPesanan(this);
        hitungTotal();
    }

    public BigDecimal hitungTotal() {
        totalBiaya = items.stream()
                .map(ItemPesanan::hitungSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalBiaya;
    }

    public void updateStatus(StatusPesanan statusBaru) { this.status = statusBaru; }
    public void setPembayaran(Pembayaran pembayaran) { this.pembayaran = pembayaran; }
    public String getIdPesanan() { return idPesanan; }
    public LocalDateTime getTanggal() { return tanggal; }
    public BigDecimal getTotalBiaya() { return totalBiaya; }
    public StatusPesanan getStatus() { return status; }
    public Pelanggan getPelanggan() { return pelanggan; }
    public List<ItemPesanan> getItems() { return items; }
    public Pembayaran getPembayaran() { return pembayaran; }
}