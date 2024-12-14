package com.samdev.ecommerce.payment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record Customer(
    String id,
    @NotNull(message = "Firstname should not be null")
    @NotEmpty(message = "Firstname should not be null")
    String firstname,
    @NotNull(message = "Lastname should not be null")
    @NotEmpty(message = "Lastname should not be null")
    String lastname,
    @NotNull(message = "Email should not be null")
    @NotEmpty(message = "Email should not be null")
    @Email(message = "Email not correctly formatted")
    String email
) {
}
