package com.quickbite.quickbite.boundary.gateway;

import com.quickbite.quickbite.boundary.PaymentGatewayAPI;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.UUID;

@Component
public class MockPaymentGateway implements PaymentGatewayAPI {

    @Override
    public String mintaTransaksiPembayaran(BigDecimal total, String tokenKartu) {
        if (total == null || total.signum() <= 0) {
            throw new IllegalArgumentException("Total pembayaran harus lebih dari nol.");
        }

        if (tokenKartu == null || tokenKartu.isBlank()
                || tokenKartu.toUpperCase().startsWith("FAIL")) {
            throw new IllegalStateException("Simulasi pembayaran gagal.");
        }

        return "QB-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}