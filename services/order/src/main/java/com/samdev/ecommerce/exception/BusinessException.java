package com.samdev.ecommerce.exception;

public class BusinessException extends  RuntimeException{

    private final String msg;

    public BusinessException(String msg) {
        this.msg = msg;
    }
}
