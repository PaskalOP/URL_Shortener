package com.example.URL_Shortener.config.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Клас для конфігурації енкодера паролів.
 * Включає налаштування для використання BCryptPasswordEncoder.
 */
@Component
public class EncoderConfig {

    /**
     * Метод, який повертає бін PasswordEncoder
     * Використовується для шифрування паролів за допомогою алгоритму BCrypt.
     *
     * @return PasswordEncoder - інтерфейс для шифрування паролів
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
