package com.samdev.ecommerce.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
      String id,
      @NotNull(message = "Customer first name is required")
      String firstname,
      @NotNull(message = "Customer last name is required")
      String lastname,
      @Email(message = "Customer email is not a valid email address")
      @NotNull(message = "Customer email is required")
      String email,
      Address address) {

}
