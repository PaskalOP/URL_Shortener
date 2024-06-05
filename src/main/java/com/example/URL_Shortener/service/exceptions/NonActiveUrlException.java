package com.example.URL_Shortener.service.exceptions;


public class NonActiveUrlException extends RuntimeException {
    public NonActiveUrlException(String message) {
        super(message);
    }
}
