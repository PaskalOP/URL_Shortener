package com.example.URL_Shortener.service.exceptions;

import lombok.Getter;

@Getter
public class NonActiveUrlException extends RuntimeException {
    private String invalidUrl;

    public NonActiveUrlException(String message, String invalidUrl) {
        super(message);
        this.invalidUrl = invalidUrl;
    }
}
