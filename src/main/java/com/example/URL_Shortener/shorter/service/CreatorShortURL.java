package com.example.URL_Shortener.shorter.service;

import com.example.URL_Shortener.config.Config;
import com.example.URL_Shortener.shorter.data.entity.EntityURL;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

/**
 * Сервісний клас для створення коротких URL.
 * Цей клас містить метод для генерування короткого URL з оригінального URL, встановлює
 * дату створення і дату закінчення дії, а також ініціалізує лічильник використань.
 */
@Service
@RequiredArgsConstructor
public class CreatorShortURL {
    private final Config config;

    /**
     * Метод створює новий об'єкт NewShortURL з вказаною оригінальною URL.
     *
     * @param originURL Оригінальна URL, яку потрібно скоротити.
     * @return Об'єкт NewShortURL з заповненими полями.
     */

    public EntityURL createShortURL(String originURL, String login) {
        // Генеруємо короткий URL
        String shortURL = generateShortURL();

        // Формуємо повний короткий URL
        String fullShortURL = "http://localhost:9999/" + shortURL;
        System.out.println(config.getValidityDays());

        // Повертаємо новий об'єкт EntityURL
        return new EntityURL(
                originURL,
                fullShortURL,
                0L,
                login,
                LocalDate.now(),
                LocalDate.now().plusDays(config.getValidityDays())
        );
    }


    /**
     * Метод генерує випадковий короткий URL довжиною від 6 до 8 символів.
     *
     * @return Згенерована коротка URL.
     */
    public String generateShortURL() { // public суто ради того щоб тест пішов по дефолту private
        int length = new Random().nextInt(3) + 6; // Генерує довжину від 6 до 8 символів
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // Допустимі символи
        StringBuilder shortURL = new StringBuilder(length); // Створюємо StringBuilder для згенерованого URL
        Random random = new Random(); // Створюємо об'єкт Random для генерації випадкових чисел
        for (int i = 0; i < length; i++) {
            shortURL.append(characters.charAt(random.nextInt(characters.length()))); // Додаємо випадковий символ до shortURL
        }
        return shortURL.toString(); // Повертаємо згенеровану коротку URL
    }
}