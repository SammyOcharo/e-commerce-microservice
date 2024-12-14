package com.samdev.ecommerce.Exception;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class CustomerNotFoundException extends RuntimeException {

    private final String msg;

    public CustomerNotFoundException(String msg) {
        this.msg = msg;
    }
}
