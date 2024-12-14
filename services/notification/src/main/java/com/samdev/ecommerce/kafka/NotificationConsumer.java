package com.samdev.ecommerce.kafka;

import com.samdev.ecommerce.email.EmailService;
import com.samdev.ecommerce.kafka.order.OrderConfirmation;
import com.samdev.ecommerce.kafka.payment.PaymentMethod;
import com.samdev.ecommerce.kafka.payment.PaymentNotificationRequest;
import com.samdev.ecommerce.notification.Notification;
import com.samdev.ecommerce.notification.NotificationRepository;
import com.samdev.ecommerce.notification.NotificationType;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class NotificationConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    public NotificationConsumer(NotificationRepository notificationRepository, EmailService emailService) {
        this.notificationRepository = notificationRepository;
        this.emailService = emailService;
    }

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentNotificationRequest paymentConfirmation) throws MessagingException {
        log.info("Consuming the message from payment payment:: {}", paymentConfirmation);
        System.out.println("This is the payment method: " + paymentConfirmation.paymentMethod());

        Notification notification = new Notification();
        notification.getNotificationType(NotificationType.PAYMENT_CONFIRMATION);
        notification.setNotificationDate(LocalDateTime.now());
        notification.setPaymentConfirmation(paymentConfirmation);
        notificationRepository.save(notification);


        //send email
        var customerName = paymentConfirmation.customerFirstName() + " " + paymentConfirmation.customerLastName();
        emailService.sendPaymentSuccessEmail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.customerEmail(),
                paymentConfirmation.amount(),
                paymentConfirmation.paymentMethod(),
                paymentConfirmation.orderReference()
        );


    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Consuming the message from order topic:: {}", orderConfirmation);

        Notification notification = new Notification();
        notification.getNotificationType(NotificationType.ORDER_CONFIRMATION);
        notification.setNotificationDate(LocalDateTime.now());
        notification.setOrderConfirmation(orderConfirmation);
        notificationRepository.save(notification);


        //send email
        var customerName = orderConfirmation.customer().firstname() + " " + orderConfirmation.customer().lastname();
        log.info("This is the list of products: {}", orderConfirmation);
        log.info("This is the customer full name: {}", customerName);
        emailService.sendOrderConfirmationEmail(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.paymentMethod(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );

    }
}
