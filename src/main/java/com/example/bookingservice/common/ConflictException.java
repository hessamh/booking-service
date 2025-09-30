package com.example.bookingservice.common;

public class ConflictException extends RuntimeException{

    public ConflictException(String message) {
        super(message);
    }
}
