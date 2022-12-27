package com.order.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationExceptions(AuthenticationFailedException exception, WebRequest webRequest) {
        ErrorResponse response = ErrorResponse.builder()
                .dateTime(LocalDateTime.now())
                .errorCode(401)
                .status(HttpStatus.UNAUTHORIZED)
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExceptions(Exception exception, WebRequest webRequest) {
        ErrorResponse response = ErrorResponse.builder()
                .dateTime(LocalDateTime.now())
                .errorCode(500)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<ErrorResponse> handleCustomValidationException(CustomValidationException exception, WebRequest webRequest) {
        ErrorResponse response = ErrorResponse.builder()
                .dateTime(LocalDateTime.now())
                .errorCode(400)
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundExceptions(ResourceNotFound exception, WebRequest webRequest) {
        ErrorResponse response = ErrorResponse.builder()
                .dateTime(LocalDateTime.now())
                .errorCode(404)
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceFound.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResourceFoundException(DuplicateResourceFound exception, WebRequest webRequest) {
        ErrorResponse response = ErrorResponse.builder()
                .dateTime(LocalDateTime.now())
                .errorCode(409)
                .status(HttpStatus.CONFLICT)
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(response,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidStatusException.class)
    public ResponseEntity<ErrorResponse> handleInvalidStatusException(InvalidStatusException exception, WebRequest webRequest) {
        ErrorResponse response = ErrorResponse.builder()
                .dateTime(LocalDateTime.now())
                .errorCode(500)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
