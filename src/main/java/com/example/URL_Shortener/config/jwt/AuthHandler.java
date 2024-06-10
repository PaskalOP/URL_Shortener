package com.example.URL_Shortener.config.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Клас для обробки невдалих спроб автентифікації.
 * Реалізує інтерфейс AuthenticationEntryPoint для обробки неавторизованих запитів.
 */
@Slf4j
@Component
public class AuthHandler implements AuthenticationEntryPoint {

    /**
     * Метод commence викликається при невдалій автентифікації
     * Логує повідомлення про помилку і відправляє відповідь з кодом 401 (Неавторизовано)
     *
     * @param request HttpServletRequest - поточний запит
     * @param response HttpServletResponse - поточна відповідь
     * @param authException AuthenticationException - виключення, що містить інформацію про помилку автентифікації
     * @throws IOException - можливе виключення вводу/виводу
     * @throws ServletException - можливе виключення сервлета
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        log.error("Unauthorized error: {}", authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }
}
