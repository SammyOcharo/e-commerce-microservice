package com.samdev.ecommerce.payment;

import com.samdev.ecommerce.customer.CustomerResponse;
import com.samdev.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
