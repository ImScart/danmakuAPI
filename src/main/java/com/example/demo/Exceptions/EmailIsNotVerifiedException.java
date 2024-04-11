package com.example.demo.Exceptions;

public class EmailIsNotVerifiedException extends RuntimeException {
    public EmailIsNotVerifiedException(String message) {
        super(message);
    }
}