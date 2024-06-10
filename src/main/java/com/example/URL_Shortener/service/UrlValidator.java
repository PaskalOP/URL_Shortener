package com.example.URL_Shortener.service;

import com.example.URL_Shortener.service.exceptions.InvalidUrlException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

/**
 * Клас для перевірки валідності URL-адреси.
 */
@Component
public class UrlValidator {
    private static final String URL_PATTERN = "^(http|https)://.*$";
    private static final String SHORT_URL_PATTERN = "^http://localhost:9999/shorter/[a-zA-Z0-9]{6,8}$";

    /**
     * Перевіряє, чи є URL-адреса валідною.
     *
     * @param urlString URL-адреса для перевірки.
     * @return true, якщо URL-адреса є валідною, в іншому випадку викидає виключення InvalidUrlException.
     * @throws InvalidUrlException викидається, якщо URL-адреса є невалідною.
     */
    public boolean isValidUrl(String urlString) throws InvalidUrlException {
        if(!Pattern.compile(URL_PATTERN).matcher(urlString).matches())
            throw new InvalidUrlException("URL must start with 'http://' or 'https://'", urlString);
        return isValidConnection(urlString);
    }

    /**
     * Перевіряє, чи можна підключитися до URL-адреси.
     *
     * @param urlString URL-адреса для перевірки.
     * @return true, якщо можна підключитися до URL-адреси та статус-код є валідним
     * @throws InvalidUrlException викидається, якщо не можна підключитися до URL-адреси або отриманий недійсний статус-код.
     */
    private boolean isValidConnection(String urlString) throws InvalidUrlException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            int statusCode = connection.getResponseCode();
            if(isValidStatusCode(statusCode))
                return true;
            else throw new InvalidUrlException("Failed to connect to URL. Status code: " + statusCode , urlString);
        } catch (IOException e) {
            throw new InvalidUrlException("Failed to connect to URL", urlString);
        } finally {
            if (connection != null)
                connection.disconnect();
        }
    }

    /**
     * Перевіряє, чи є короткий URL валідним.
     *
     * @param shortUrlString короткий URL для перевірки.
     * @return true, якщо короткий URL є валідним.
     * @throws InvalidUrlException викидається, якщо короткий URL є невалідним.
     */
    public boolean isValidShortUrl(String shortUrlString) throws InvalidUrlException {
        if(!Pattern.compile(SHORT_URL_PATTERN).matcher(shortUrlString).matches())
            throw new InvalidUrlException("Short URL must start with 'http://localhost:9999/shorter/' and contain 6-8 characters after it", shortUrlString);
        return true;
    }
    /**
     * Перевіряє, чи є статус-код валідним.
     *
     * @param statusCode статус-код для перевірки.
     * @return true, якщо статус-код є валідним, в іншому випадку false.
     */
    private boolean isValidStatusCode(int statusCode) {
        return statusCode == HttpURLConnection.HTTP_FORBIDDEN ||
                statusCode == HttpURLConnection.HTTP_UNAUTHORIZED ||
                statusCode < HttpURLConnection.HTTP_BAD_REQUEST ||
                statusCode >= HttpURLConnection.HTTP_INTERNAL_ERROR;
    }
}