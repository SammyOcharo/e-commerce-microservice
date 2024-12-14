package com.samdev.ecommerce.notification;

import com.samdev.ecommerce.kafka.order.OrderConfirmation;
import com.samdev.ecommerce.kafka.payment.PaymentNotificationRequest;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class Notification {
    @Id
    private String id;
    private NotificationType notificationType;
    private LocalDateTime notificationDate;
    private OrderConfirmation orderConfirmation;
    private PaymentNotificationRequest paymentConfirmation;

    public Notification() {
    }

    public Notification(String id, NotificationType notificationType, LocalDateTime notificationDate, OrderConfirmation orderConfirmation, PaymentNotificationRequest paymentConfirmation) {
        this.id = id;
        this.notificationType = notificationType;
        this.notificationDate = notificationDate;
        this.orderConfirmation = orderConfirmation;
        this.paymentConfirmation = paymentConfirmation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NotificationType getNotificationType(NotificationType paymentConfirmation) {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public LocalDateTime getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(LocalDateTime notificationDate) {
        this.notificationDate = notificationDate;
    }

    public OrderConfirmation getOrderConfirmation() {
        return orderConfirmation;
    }

    public void setOrderConfirmation(OrderConfirmation orderConfirmation) {
        this.orderConfirmation = orderConfirmation;
    }

    public PaymentNotificationRequest getPaymentConfirmation() {
        return paymentConfirmation;
    }

    public void setPaymentConfirmation(PaymentNotificationRequest paymentConfirmation) {
        this.paymentConfirmation = paymentConfirmation;
    }
}
