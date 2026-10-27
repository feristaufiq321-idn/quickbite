package com.quickbite.quickbite.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ItemCheckoutRequest {
    @NotBlank
    private String idMenu;

    @Min(1)
    private int jumlah;

    public ItemCheckoutRequest() {}

    public String getIdMenu() { return idMenu; }
    public void setIdMenu(String idMenu) { this.idMenu = idMenu; }

    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
}