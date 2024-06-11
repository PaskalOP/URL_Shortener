package com.example.URL_Shortener.security.exception;

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(String username)  {
        super("User " + username +" already exist");
    }
}
