package com.example.URL_Shortener;

import com.example.URL_Shortener.entity.NewShortURL;
import com.example.URL_Shortener.responseDTO.ResponseInvalidDTO;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@RestController
@RequestMapping("/api/v1")
public class CreatorShortURL {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int SHORT_URL_LENGTH = 6;

    @Data
    public static class OriginURL {
        private String originalURL;
    }

    // Метод для створення короткого URL на основі вказаного початкового URL
    @PostMapping("/shorten")
    public Object createShortURL(@RequestBody OriginURL originURL) {
        String originalURL = originURL.getOriginalURL();

        // Перевірка дійсності вхідного URL
        if (!isValidURL(originalURL)) {
            return new ResponseInvalidDTO(originalURL, "Недійсний формат URL.");
        }

        // Генерація короткого URL
        String shortURL = generateShortUrl();
        // Отримання поточної дати і часу
        LocalDate createDate = LocalDate.now();
        // Отримання дати завершення активності (10 днів після поточної дати)
        LocalDate finishingDate = createDate.plusDays(10);
        // Повернення короткого URL у форматі JSON
        return new NewShortURL(originalURL, shortURL, createDate, finishingDate);
    }
    //TODO коли зявиться БД робимо репозиторій.
//    @GetMapping("/{shortUrl}")
//    public String redirect(@PathVariable String shortUrl) {
//        NewShortURL newShortURL = shortURLRepository.findByShortURL(shortUrl);
//        if (newShortURL != null) {
//            // Перевірка чи коротке посилання ще активне
//            if (LocalDate.now().isBefore(newShortURL.getExpireDate())) {
//                return "redirect:" + newShortURL.getOriginURL();
//            } else {
//                return "Short URL has expired.";
//            }
//        } else {
//            return "Short URL not found.";
//        }
//    }


    // Метод для отримання статистики про короткий URL за його скороченим URL
    @GetMapping("/stats/{shortUrl}")
    public Object getURLStats(@PathVariable String shortUrl) {
        // Повернення статистики короткого URL у форматі JSON
        return new ResponseInvalidDTO(shortUrl, "Короткий URL не знайдено.");
    }

    // Метод для перевірки дійсності URL
    private boolean isValidURL(String url) {
        // TODO логіку перевірки URL тут
        // Наразі, будемо вважати будь-який непорожній URL дійсним
        return url != null;
    }

    // Метод для генерації короткого URL
    private String generateShortUrl() {
        Random random = new Random();
        StringBuilder shortUrl = new StringBuilder(SHORT_URL_LENGTH);
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            shortUrl.append(CHARACTERS.charAt(index));
        }
        return shortUrl.toString();
    }
}