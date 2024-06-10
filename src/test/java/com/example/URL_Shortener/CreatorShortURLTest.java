package com.example.URL_Shortener;

import static org.junit.jupiter.api.Assertions.*;

import com.example.URL_Shortener.service.CreatorShortURL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class CreatorShortURLTest {
    private String originURL; // Початковий URL
   // private NewShortURL newShortURL; // Створений короткий URL
    private LocalDate expectedDate; // Очікувана дата створення
    private LocalDate expectedFinishingDate; // Очікувана дата завершення

    @BeforeEach
    void setUp() {
        // Ініціалізуємо об'єкти перед кожним тестом
        CreatorShortURL creatorShortURL = new CreatorShortURL();
        originURL = "https://goit.global/ua"; // Задаємо початковий URL
        // newShortURL = creatorShortURL.createShortURL(originURL); // Створюємо короткий URL
        expectedDate = LocalDate.now(); // Очікувана дата створення - сьогодні
        expectedFinishingDate = LocalDate.now().plusDays(10); // Очікувана дата завершення через 10 днів
    }

    @Test
    void testCreateShortURLNotNull() {
       // assertNotNull(newShortURL); // Перевіряємо, що створений короткий URL не є null
    }

    @Test
    void testCreateShortURLOriginURL() {
        //assertEquals(originURL, newShortURL.getOriginURL()); // Перевіряємо, що початковий URL співпадає
    }

    @Test
    void testCreateShortURLShortURL() {
       // assertNotNull(newShortURL.getShortURL()); // Перевіряємо, що короткий URL не є null
       // assertTrue(newShortURL.getShortURL().startsWith("http://localhost:")); // Перевіряємо, що короткий URL починається з "http://localhost"
    }

    @Test
    void testCreateShortURLCreatingDate() {
        //assertEquals(expectedDate, newShortURL.getCreatingDate()); // Перевіряємо, що дата створення - сьогодні
    }

    @Test
    void testCreateShortURLFinishingDate() {
        //assertNotEquals(expectedFinishingDate, newShortURL.getFinishingDate()); // Перевіряємо, що дата закінчення відповідає конфігурації
    }

    @Test
    void testCreateShortURLCountUse() {
       // assertEquals(0L, newShortURL.getCountUse()); // Перевіряємо, що кількість використань спочатку дорівнює 0
    }
}