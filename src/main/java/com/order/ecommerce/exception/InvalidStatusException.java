package com.order.ecommerce.exception;

public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException(String s) {
        super(s);
    }
    public InvalidStatusException() {
        super();
    }
}
