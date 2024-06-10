package com.example.URL_Shortener.service.exceptions;

public class UserNotFoundException extends Exception {
    private static final String USER_NOT_FOUND_EXCEPTION_TEXT = "User with login = %s not found.";

    public UserNotFoundException(String login) {
        super(String.format(USER_NOT_FOUND_EXCEPTION_TEXT, login));
    }
}