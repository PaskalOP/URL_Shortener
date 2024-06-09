package com.example.URL_Shortener.service;

import com.example.URL_Shortener.entity.EntityURL;
import com.example.URL_Shortener.service.exceptions.InvalidUrlException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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
     * Перевіряє, чи є короткий URL дійсним та не існує у базі даних.
     *
     * @param shortUrl короткий URL, який потрібно перевірити
     * @return true, якщо URL дійсний та не існує, інакше кидає виняток InvalidUrlException
     */
    public boolean validShortUrl(String shortUrl) {
        EntityURL entityURL = new EntityURL();
        try {
            entityURL = urlService.findByShortURL(shortUrl);

        } catch (InvalidUrlException e) {
            entityURL = null;
        }
        // Перевіряємо, чи є короткий URL дійсним і не існує у базі даних
        if ((urlValidator.isValidShortUrl(shortUrl))
                && (entityURL == null)) return true;
        else throw new InvalidUrlException("Short URL is not valid", shortUrl);
    }

    /**
     * Перевіряє, чи є оригінальний URL дійсним.
     *
     * @param originalUrl оригінальний URL, який потрібно перевірити
     * @return true, якщо URL дійсний, інакше кидає виняток InvalidUrlException
     */
    public boolean validOriginalUrl(String originalUrl) {
        if (urlValidator.isValidUrl(originalUrl)) return true;
        else throw new InvalidUrlException("Original URL is not valid", originalUrl);
    }

    /**
     * Перевіряє, чи існує користувач із вказаним логіном.
     *
     * @param login логін користувача, якого потрібно перевірити
     * @return true, якщо користувач існує, інакше кидає виняток InvalidUrlException
     */
    public boolean validLogin(String login) {
        if (userService.findUserByLogin(login) != null) return true;
        else throw new InvalidUrlException("This user is not exist", login);
    }

    /**
     * Перевіряє, чи є дата дійсною та не є в минулому.
     *
     * @param date дата у форматі "uuuu-MM-dd", яку потрібно перевірити
     * @return true, якщо дата дійсна, інакше кидає виняток InvalidUrlException
     */
    public boolean validData(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
            LocalDate dateFormat = LocalDate.parse(date, formatter);
            if (dateFormat.isBefore(LocalDate.now())) {
                throw new InvalidUrlException("Date can't be in the past", date);
            }
            return true;
        } catch (DateTimeParseException e) {
            throw new InvalidUrlException("Invalid date format", date);
        }
    }
}