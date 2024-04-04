package com.example.demo.Exceptions;

public class ExpiredOrInvalidTokenException extends RuntimeException {
    public ExpiredOrInvalidTokenException(String message) {
        super(message);
    }
}