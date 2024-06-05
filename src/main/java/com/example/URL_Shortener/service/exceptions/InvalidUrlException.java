package com.example.URL_Shortener.service.exceptions;

import java.io.IOException;

public class InvalidUrlException extends IOException {
    public InvalidUrlException(String message) {
        super(message);
    }
}
