package com.order.ecommerce.exception;

public class AuthenticationFailedException extends RuntimeException{
    public AuthenticationFailedException() {
        super();
    }
    public AuthenticationFailedException(String s) {
        super(s);
    }
}
