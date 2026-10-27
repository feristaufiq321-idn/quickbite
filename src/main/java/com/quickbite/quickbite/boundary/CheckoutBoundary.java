
package com.quickbite.quickbite.boundary;

import com.quickbite.quickbite.controller.CheckoutController;
import com.quickbite.quickbite.controller.dto.CheckoutRequest;
import com.quickbite.quickbite.controller.dto.CheckoutResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutBoundary {

    private final CheckoutController checkoutController;

    public CheckoutBoundary(CheckoutController checkoutController) {
        this.checkoutController = checkoutController;
    }

    @PostMapping
    public ResponseEntity<CheckoutResponse> checkout(
            @Valid @RequestBody CheckoutRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(checkoutController.checkout(request));
    }

    @PostMapping("/{idPesanan}/batal")
    public ResponseEntity<String> batalkanPesanan(
            @PathVariable String idPesanan,
            @RequestBody(required = false) PembatalanRequest request) {

        String alasan = request == null ? null : request.alasan();

        checkoutController.batalkanPesanan(idPesanan, alasan);

        return ResponseEntity.ok("Pesanan berhasil dibatalkan.");
    }

    public record PembatalanRequest(String alasan) {
    }
}

