package com.samdev.ecommerce.order;

import java.math.BigDecimal;
import java.math.BigInteger;

public record OrderResponse(
        Integer id,
        String reference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerId
) {
}
