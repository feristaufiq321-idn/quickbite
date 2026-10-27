package com.quickbite.quickbite.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @Column(name = "id_menu", length = 30)
    private String idMenu;

    @Column(name = "nama_menu", nullable = false, length = 120)
    private String namaMenu;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal harga;

    @Column(nullable = false)
    private int stok;

    protected Menu() {
    }

    public Menu(String idMenu, String namaMenu,
                BigDecimal harga, int stok) {
        this.idMenu = idMenu;
        this.namaMenu = namaMenu;
        this.harga = harga;
        this.stok = stok;
    }

    public boolean validasiStok(int jumlah) {
        return jumlah > 0 && stok >= jumlah;
    }

    public boolean kurangiStok(int jumlah) {
        if (!validasiStok(jumlah)) {
            return false;
        }

        stok -= jumlah;
        return true;
    }

    public void tambahStok(int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException(
                    "Jumlah pengembalian stok harus lebih dari 0");
        }

        stok += jumlah;
    }

    public String getIdMenu() {
        return idMenu;
    }

    public String getNamaMenu() {
        return namaMenu;
    }

    public BigDecimal getHarga() {
        return harga;
    }

    public int getStok() {
        return stok;
    }
}
