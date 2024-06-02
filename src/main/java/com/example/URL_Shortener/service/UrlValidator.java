package com.example.URL_Shortener.service;

import com.example.URL_Shortener.service.exceptions.InvalidUrlException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

/**
 * Клас для перевірки валідності URL-адреси.
 */
@Component
public class UrlValidator {
    private static final String URL_PATTERN = "^(http|https)://.*$";
    private static final Pattern pattern = Pattern.compile(URL_PATTERN);

    /**
     * Перевіряє, чи є URL-адреса валідною.
     *
     * @param urlString URL-адреса для перевірки.
     * @return true, якщо URL-адреса є валідною, в іншому випадку викидає виключення InvalidUrlException.
     * @throws InvalidUrlException викидається, якщо URL-адреса є невалідною.
     */
    public boolean isValidUrl(String urlString) throws InvalidUrlException {
        return isValidConnection(urlString) && pattern.matcher(urlString).matches();
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
            return isValidStatusCode(statusCode);
        } catch (IOException e) {
            throw new InvalidUrlException("Failed to validate URL: " + urlString);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
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