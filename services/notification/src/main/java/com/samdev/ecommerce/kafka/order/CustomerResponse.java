package com.samdev.ecommerce.kafka.order;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email
) {
}
