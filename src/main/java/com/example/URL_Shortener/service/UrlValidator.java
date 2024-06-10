package com.example.URL_Shortener.service;

import com.example.URL_Shortener.service.exceptions.InvalidUrlException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Pattern;

/**
 * Клас для перевірки валідності URL-адреси.
 */
@Component
public class UrlValidator {
    private final String URL_PATTERN = "^(http|https)://.*$";
    private final Pattern SHORT_URL_PATTERN = Pattern.compile("/[a-zA-Z0-9]{6,8}($|/.*)");

    /**
     * Перевіряє, чи є URL-адреса валідною.
     *
     * @param urlString URL-адреса для перевірки.
     * @return true, якщо URL-адреса є валідною, в іншому випадку викидає виключення InvalidUrlException.
     * @throws InvalidUrlException викидається, якщо URL-адреса є невалідною.
     */
    public boolean isValidUrl(String urlString) throws InvalidUrlException {
        if (!Pattern.compile(URL_PATTERN).matcher(urlString).matches())
            throw new InvalidUrlException("URL must start with 'http://' or 'https://'", urlString);
        return isValidConnection(urlString);
    }

    /**
     * Перевіряє, чи можна підключитися до URL-адреси.
     *
     * @param urlString URL-адреса для перевірки.
     * @return true, якщо можна підключитися до URL-адреси та статус-код є валідним.
     * @throws InvalidUrlException викидається, якщо не можна підключитися до URL-адреси або отриманий недійсний статус-код.
     */
    private boolean isValidConnection(String urlString) throws InvalidUrlException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .build();
        try {
            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
            int statusCode = response.statusCode();
            if (isValidStatusCode(statusCode)) {
                return true;
            } else {
                throw new InvalidUrlException("Failed to connect to URL. Status code: " + statusCode, urlString);
            }
        } catch (IOException | InterruptedException e) {
            throw new InvalidUrlException("Failed to connect to URL", urlString);
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
        if (!SHORT_URL_PATTERN.matcher(shortUrlString).find()) {
            throw new InvalidUrlException("Short URL must be 6-8 characters", shortUrlString);
        }
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