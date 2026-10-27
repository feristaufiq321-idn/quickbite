package com.quickbite.quickbite.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class CheckoutRequest {
    @NotBlank
    private String idPelanggan;

    @NotBlank
    private String tokenKartu;

    @NotBlank
    private String metodePembayaran;

    @NotEmpty
    @Valid
    private List<@Valid ItemCheckoutRequest> keranjang;

    public CheckoutRequest() {}

    public String getIdPelanggan() { return idPelanggan; }
    public void setIdPelanggan(String idPelanggan) { this.idPelanggan = idPelanggan; }

    public String getTokenKartu() { return tokenKartu; }
    public void setTokenKartu(String tokenKartu) { this.tokenKartu = tokenKartu; }

    public String getMetodePembayaran() { return metodePembayaran; }
    public void setMetodePembayaran(String metodePembayaran) { this.metodePembayaran = metodePembayaran; }

    public List<ItemCheckoutRequest> getKeranjang() { return keranjang; }
    public void setKeranjang(List<ItemCheckoutRequest> keranjang) { this.keranjang = keranjang; }
}