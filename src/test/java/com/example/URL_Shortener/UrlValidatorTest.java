package com.example.URL_Shortener;

import com.example.URL_Shortener.shorter.exceptions.InvalidUrlException;
import com.example.URL_Shortener.shorter.service.UrlValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class UrlValidatorTest {
    private UrlValidator urlValidator;

    @BeforeEach
    public void setUp() {
        urlValidator = new UrlValidator();
    }

    @Test
    void testValidUrl() throws InvalidUrlException {
        //Перевірка валідатора на статус код 200(ОК)
        Assertions.assertTrue(urlValidator.isValidUrl("https://blogs.nasa.gov/boeing-crew-flight-test/"));
        //Перевірка валідатора на статус код 200(Неавторизовано)
        Assertions.assertTrue(urlValidator.isValidUrl("https://httpstat.us/401"));
        //Перевірка валідатора на статус код 403(Недостатньо прав)
        Assertions.assertTrue(urlValidator.isValidUrl("https://httpstat.us/403"));
        //Перевірка валідатора на статус код 300(Перенаправлення)
        Assertions.assertTrue(urlValidator.isValidUrl("http://httpstat.us/300"));
        //Перевірка валідатора на статус код 500(Помилка сервера)
        Assertions.assertTrue(urlValidator.isValidUrl("https://httpstat.us/500"));
    }

    @Test
    void testInvalidUrlThrowsException() {
        //Перевірка валідатора на неправильну адресу посилання
        Assertions.assertThrows(InvalidUrlException.class, () -> {
            urlValidator.isValidUrl("https://httpbin.org/status/400");
        });
        Assertions.assertThrows(InvalidUrlException.class, () -> {
            urlValidator.isValidUrl("dsdada");
        });
        Assertions.assertThrows(InvalidUrlException.class, () -> {
            urlValidator.isValidUrl("https://httpstat.us/420");
        });
        Assertions.assertThrows(InvalidUrlException.class, () -> {
            urlValidator.isValidUrl("https://httpstat.us/450");
        });
        Assertions.assertThrows(InvalidUrlException.class, () -> {
            urlValidator.isValidUrl("https://httpstat.us/404");
        });
    }
}