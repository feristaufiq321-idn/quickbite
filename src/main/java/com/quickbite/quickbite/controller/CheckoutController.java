
package com.quickbite.quickbite.controller;

import com.quickbite.quickbite.boundary.PaymentGatewayAPI;
import com.quickbite.quickbite.controller.dto.CheckoutRequest;
import com.quickbite.quickbite.controller.dto.CheckoutResponse;
import com.quickbite.quickbite.controller.dto.ItemCheckoutRequest;
import com.quickbite.quickbite.entity.*;
import com.quickbite.quickbite.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CheckoutController {

    private final PelangganRepository pelangganRepository;
    private final MenuRepository menuRepository;
    private final PesananRepository pesananRepository;
    private final PaymentGatewayAPI paymentGateway;

    public CheckoutController(
            PelangganRepository pelangganRepository,
            MenuRepository menuRepository,
            PesananRepository pesananRepository,
            PaymentGatewayAPI paymentGateway) {
        this.pelangganRepository = pelangganRepository;
        this.menuRepository = menuRepository;
        this.pesananRepository = pesananRepository;
        this.paymentGateway = paymentGateway;
    }

    // Membatalkan pesanan dan mengembalikan stok menu
    @Transactional
    public void batalkanPesanan(String idPesanan, String alasan) {
        Pesanan pesanan = pesananRepository.findById(idPesanan)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pesanan tidak ditemukan."));

        if (pesanan.getStatus() == StatusPesanan.BATAL) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Pesanan sudah dibatalkan.");
        }

        if (pesanan.getStatus() != StatusPesanan.DIPROSES) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Pesanan tidak dapat dibatalkan pada status ini.");
        }

        for (ItemPesanan item : pesanan.getItems()) {
            Menu menu = menuRepository
                    .cariDanKunciStok(item.getMenu().getIdMenu())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Menu tidak ditemukan."));

            menu.tambahStok(item.getJumlah());
        }

        pesanan.updateStatus(StatusPesanan.BATAL);
        pesananRepository.save(pesanan);
    }

    // Memproses checkout dan pembayaran
    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) {
        Pelanggan pelanggan = pelangganRepository
                .findById(request.getIdPelanggan())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pelanggan tidak ditemukan."));

        Pesanan pesanan = new Pesanan(
                "ORD-" + UUID.randomUUID().toString().substring(0, 8),
                LocalDateTime.now(),
                pelanggan
        );

        for (ItemCheckoutRequest itemRequest : request.getKeranjang()) {
            Menu menu = menuRepository
                    .cariDanKunciStok(itemRequest.getIdMenu())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Menu tidak ditemukan: "
                                    + itemRequest.getIdMenu()));

            if (!menu.kurangiStok(itemRequest.getJumlah())) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Stok tidak mencukupi untuk menu: "
                                + menu.getNamaMenu()
                                + " (Sisa: " + menu.getStok() + ")");
            }

            ItemPesanan item = new ItemPesanan(
                    "ITM-" + UUID.randomUUID().toString().substring(0, 8),
                    itemRequest.getJumlah(),
                    menu
            );

            pesanan.tambahItem(item);
        }

        pesanan.hitungTotal();

        String kodeReferensi;
        try {
            kodeReferensi = paymentGateway.mintaTransaksiPembayaran(
                    pesanan.getTotalBiaya(),
                    request.getTokenKartu()
            );
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Pembayaran simulasi gagal. Pesanan tidak disimpan.",
                    ex
            );
        }

        Pembayaran pembayaran = new Pembayaran(
                "PAY-" + UUID.randomUUID().toString().substring(0, 8),
                pesanan,
                pesanan.getTotalBiaya(),
                request.getMetodePembayaran(),
                "SUCCESS",
                kodeReferensi
        );

        if (pembayaran.prosesVerifikasi()) {
            pesanan.updateStatus(StatusPesanan.DIPROSES);
        } else {
            pesanan.updateStatus(StatusPesanan.BATAL);
        }

        pesanan.setPembayaran(pembayaran);
        pesananRepository.save(pesanan);

        return new CheckoutResponse(
                pesanan.getIdPesanan(),
                pembayaran.getIdPembayaran(),
                pesanan.getTotalBiaya(),
                pesanan.getStatus().name(),
                pembayaran.getStatusBayar(),
                pembayaran.getKodeReferensi(),
                pesanan.getTanggal()
        );
    }
}
