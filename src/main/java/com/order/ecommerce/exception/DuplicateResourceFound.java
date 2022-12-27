package com.order.ecommerce.exception;

public class DuplicateResourceFound extends RuntimeException{

    public DuplicateResourceFound() {
        super();
    }

    public DuplicateResourceFound(String message) {
        super(message);
    }
}
