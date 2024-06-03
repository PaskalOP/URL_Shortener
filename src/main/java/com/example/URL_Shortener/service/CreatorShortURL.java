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
    @Value("${short.url.template}")
    private String shortUrlTemplate;

    @Value("${short.url.validity.days}")
    private int validityDays;

    /**
     * Метод створює новий об'єкт NewShortURL з вказаною оригінальною URL.
     *
     * @param originURL Оригінальна URL, яку потрібно скоротити.
     * @return Об'єкт NewShortURL з заповненими полями.
     */
    public NewShortURL createShortURL(String originURL) {
        String shortURL = generateShortURL();
        String formattedShortURL = shortUrlTemplate.replace("{shortURL}", shortURL);

        NewShortURL newShortURL = new NewShortURL();
        newShortURL.setOriginURL(originURL); // Встановлюємо оригінальну URL
        newShortURL.setShortURL(generateShortURL()); // Генеруємо коротку URL
        newShortURL.setCreatingDate(LocalDate.now()); // Встановлюємо дату створення
        newShortURL.setFinishingDate(newShortURL.getCreatingDate().plusDays(validityDays)); // Встановлюємо дату закінчення (10 днів після створення)
        newShortURL.setCountUse(0L); // Ініціалізуємо лічильник використань
        return newShortURL;
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