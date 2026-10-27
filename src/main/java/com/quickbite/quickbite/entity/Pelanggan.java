package com.quickbite.quickbite.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "pelanggan")
public class Pelanggan {

    @Id
    @Column(name = "id_pelanggan", length = 30)
    private String idPelanggan;

    @Column(nullable = false, length = 100)
    private String nama;

    @Column(length = 120)
    private String email;

    @Column(name = "nomor_telepon", length = 20)
    private String nomorTelepon;

    @Column(length = 255)
    private String alamat;

    protected Pelanggan() {
    }

    public Pelanggan(String idPelanggan, String nama, String email,
                     String nomorTelepon, String alamat) {
        this.idPelanggan = idPelanggan;
        this.nama = nama;
        this.email = email;
        this.nomorTelepon = nomorTelepon;
        this.alamat = alamat;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public String getAlamat() {
        return alamat;
    }

    public void ubahAlamat(String alamatBaru) {
        this.alamat = alamatBaru;
    }
}
