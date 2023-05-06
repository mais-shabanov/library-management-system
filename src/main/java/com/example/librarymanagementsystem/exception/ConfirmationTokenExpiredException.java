package com.example.librarymanagementsystem.exception;

public class ConfirmationTokenExpiredException extends RuntimeException {
    public ConfirmationTokenExpiredException(String message) {
        super(message);
    }
}
