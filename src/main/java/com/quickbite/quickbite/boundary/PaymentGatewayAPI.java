package com.quickbite.quickbite.boundary;

import java.math.BigDecimal;

public interface PaymentGatewayAPI {
    String mintaTransaksiPembayaran(BigDecimal total, String tokenKartu);
}