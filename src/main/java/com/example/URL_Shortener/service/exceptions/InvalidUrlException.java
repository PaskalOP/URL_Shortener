package com.example.URL_Shortener.service.exceptions;

import lombok.Getter;

@Getter
public class InvalidUrlException extends Exception {
    private String invalidUrl;

    public InvalidUrlException(String message, String invalidUrl) {
        super(message);
        this.invalidUrl = invalidUrl;
    }
}
