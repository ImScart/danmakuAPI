package com.example.demo.Exceptions;

public class ValuesInvalidException extends RuntimeException {
    public ValuesInvalidException(String message) {
        super(message);
    }
}
