package com.samdev.ecommerce.payment;

import com.samdev.ecommerce.notification.NotificationProducer;
import com.samdev.ecommerce.notification.PaymentNotificationRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    private final PaymentRepository paymentRepository;

    private final PaymentMapper mapper;
    private final  NotificationProducer notificationProducer;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper mapper, NotificationProducer notificationProducer) {
        this.paymentRepository = paymentRepository;
        this.mapper = mapper;
        this.notificationProducer = notificationProducer;
    }

    public Integer createPayment(@Valid PaymentRequest paymentRequest) {
        var payment = paymentRepository.save(mapper.toPayment(paymentRequest));
        log.info("This is the payment request customer first name: {}", paymentRequest.customer().firstname());
        log.info("This is the payment request customer first name: {}", paymentRequest.customer().lastname());
        log.info("This is the payment request customer payment method: {}", paymentRequest.paymentMethod());
        notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        paymentRequest.orderReference(),
                        paymentRequest.amount(),
                        paymentRequest.paymentMethod(),
                        paymentRequest.customer().firstname(),
                        paymentRequest.customer().lastname(),
                        paymentRequest.customer().email()
                )
        );
        return paymentRepository.save(payment).getId();
    }
}
