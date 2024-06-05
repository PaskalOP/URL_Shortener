package com.example.URL_Shortener.service;

import com.example.URL_Shortener.responseDTO.NewShortURL;
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
public class CreatorShortURL {
    @Value("${short.url.validity.days}")
    private int validityDays;

    /**
     * Метод створює новий об'єкт NewShortURL з вказаною оригінальною URL.
     *
     * @param originURL Оригінальна URL, яку потрібно скоротити.
     * @return Об'єкт NewShortURL з заповненими полями.
     */
    public NewShortURL createShortURL(String originURL) {
        // Генеруємо короткий URL
        String shortURL = generateShortURL();

        // Формуємо повний короткий URL
        String fullShortURL = "http://localhost:8080/" + shortURL;
        System.out.println("Generated Short URL: " + fullShortURL);

        // Повертаємо новий об'єкт NewShortURL
        return new NewShortURL(
                originURL,
                fullShortURL,
                0L,
                LocalDate.now(),
                LocalDate.now().plusDays(validityDays)
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