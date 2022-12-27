package com.order.ecommerce.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {

    private HttpStatus status;
    private int errorCode;
    private String message;
    private LocalDateTime dateTime;
}
