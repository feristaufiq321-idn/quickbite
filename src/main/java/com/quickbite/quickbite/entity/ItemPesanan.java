package com.quickbite.quickbite.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "item_pesanan")
public class ItemPesanan {
    @Id
    @Column(name = "id_item", length = 36)
    private String idItem;

    @Column(nullable = false)
    private int jumlah;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_pesanan", nullable = false)
    private Pesanan pesanan;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_menu", nullable = false)
    private Menu menu;

    protected ItemPesanan() {}

    public ItemPesanan(String idItem, int jumlah, Menu menu) {
        this.idItem = idItem;
        this.jumlah = jumlah;
        this.menu = menu;
        this.subtotal = hitungSubtotal();
    }

    public BigDecimal hitungSubtotal() {
        if (menu == null || jumlah <= 0) return BigDecimal.ZERO;
        subtotal = menu.getHarga().multiply(BigDecimal.valueOf(jumlah));
        return subtotal;
    }

    public void setPesanan(Pesanan pesanan) { this.pesanan = pesanan; }
    public String getIdItem() { return idItem; }
    public int getJumlah() { return jumlah; }
    public BigDecimal getSubtotal() { return subtotal; }
    public Pesanan getPesanan() { return pesanan; }
    public Menu getMenu() { return menu; }
}