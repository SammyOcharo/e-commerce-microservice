package com.samdev.ecommerce.payment;


import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {
    public Payment toPayment(@Valid PaymentRequest paymentRequest) {
        Payment payment = new Payment();
        payment.setId(paymentRequest.id());
        payment.setAmount(paymentRequest.amount());
        payment.setPaymentMethod(paymentRequest.paymentMethod());
        payment.setOrderId(paymentRequest.orderId());
        return payment;
    }
}
