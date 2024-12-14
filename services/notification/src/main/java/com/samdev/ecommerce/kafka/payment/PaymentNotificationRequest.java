package com.samdev.ecommerce.kafka.payment;

import com.samdev.ecommerce.kafka.order.CustomerResponse;

import java.math.BigDecimal;

public record PaymentNotificationRequest(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstName,
        String customerLastName,
        String customerEmail

) {
}
