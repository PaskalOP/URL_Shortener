package com.example.URL_Shortener.service.exceptions;

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(String username)  {
        super("User " + username +" already exist");
    }
}
