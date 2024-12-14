package com.samdev.ecommerce.email;

public enum EmailTemplates {
    PAYMENT_CONFIRMATION("payment-confirmation.html", "Payment successfully processed"),

    ORDER_CONFIRMATION("order-confirmation.html", "Order confirmation");

    private final String template;

    private final String subject;

    EmailTemplates(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public String getTemplate() {
        return template;
    }
}