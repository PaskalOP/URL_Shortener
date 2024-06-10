package com.example.URL_Shortener.service;

import com.example.URL_Shortener.entity.EntityURL;
import com.example.URL_Shortener.service.exceptions.InvalidUrlException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Клас для перевірки та оновлення вхідних даних.
 */
@Component
@RequiredArgsConstructor
public class ValidInputData {
    private final UrlValidator urlValidator;
    private final UserService userService;
    private final URLServiceImpl urlService;

    /**
     * Оновлює короткий URL в екземплярі EntityURL згідно з вхідними даними.
     * @param shortUrl екземпляр короткого посилання, який потрібно перевірити
     */
    public boolean validShortUrl(String shortUrl) {
       EntityURL entityURL = new EntityURL();
        try{
            entityURL =urlService.findByShortURL(shortUrl);

        }catch (InvalidUrlException e){
            entityURL =null;
        }

        if((urlValidator.isValidShortUrl(shortUrl))
                && (entityURL ==null)) return true;
        else throw new InvalidUrlException("Short URL is not valid", shortUrl);
    }

    public boolean validOriginalUrl(String originalUrl) {
       if(urlValidator.isValidUrl(originalUrl)) return true;
       else throw new InvalidUrlException("Original URL is not valid", originalUrl);
    }
    public  boolean validLogin(String login){
        if(userService.findUserByLogin(login)!=null) return true;
        else throw new InvalidUrlException("This user is not exist",login);
    }
    /**
     * Оновлює дату створення в екземплярі EntityURL згідно з вхідними даними.
     */
    public boolean validData(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
            LocalDate dateFormat = LocalDate.parse(date, formatter);
            if (dateFormat.isBefore(LocalDate.now())) {
                throw new InvalidUrlException("Date can't be in the past",date);
            }
            return true;
        } catch (DateTimeParseException e){
            throw new InvalidUrlException("Invalid date format",date);
        }
    }

}