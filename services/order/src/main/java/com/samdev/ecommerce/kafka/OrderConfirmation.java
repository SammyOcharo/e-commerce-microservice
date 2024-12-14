package com.samdev.ecommerce.kafka;

import com.samdev.ecommerce.customer.CustomerResponse;
import com.samdev.ecommerce.order.PaymentMethod;
import com.samdev.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
