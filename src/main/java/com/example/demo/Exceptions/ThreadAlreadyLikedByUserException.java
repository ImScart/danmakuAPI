package com.example.demo.Exceptions;

public class ThreadAlreadyLikedByUserException extends RuntimeException {
    public ThreadAlreadyLikedByUserException(String message) {
        super(message);
    }
}
