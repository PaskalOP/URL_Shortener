package com.example.URL_Shortener;

import com.example.URL_Shortener.service.exceptions.InvalidUrlException;
import com.example.URL_Shortener.service.UrlValidator;
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
        Assertions.assertEquals(true, urlValidator.isValidUrl("https://blogs.nasa.gov/boeing-crew-flight-test/"));
        //Перевірка валідатора на статус код 200(Неавторизовано)
        Assertions.assertEquals(true, urlValidator.isValidUrl("https://httpstat.us/401"));
        //Перевірка валідатора на статус код 403(Недостатньо прав)
        Assertions.assertEquals(true, urlValidator.isValidUrl("https://httpstat.us/403"));
        //Перевірка валідатора на статус код 300(Перенаправлення)
        Assertions.assertEquals(true, urlValidator.isValidUrl("http://httpstat.us/300"));
        //Перевірка валідатора на статус код 500(Помилка сервера)
        Assertions.assertEquals(true, urlValidator.isValidUrl("https://httpstat.us/500"));
    }

    @Test
    void testInvalidUrl() throws InvalidUrlException {
        //Перевірка валідатора на статус код 404(Не знайдено)
        Assertions.assertEquals(false, urlValidator.isValidUrl("https://httpstat.us/404"));
        //Перевірка валідатора на статус код 420(Надто багато запитів від клієнта)
        Assertions.assertEquals(false, urlValidator.isValidUrl("https://httpstat.us/420"));
        //Перевірка валідатора на статус код 450(Заблоковано батьківським контролем)
        Assertions.assertEquals(false, urlValidator.isValidUrl("https://httpstat.us/450"));
    }

    @Test
    void testInvalidUrlThrowsException() {
        //Перевірка валідатора на неправильну адресу посилання
        Assertions.assertThrows(InvalidUrlException.class, () -> {
            urlValidator.isValidUrl("hps://httpstat.us/404");
        });
        Assertions.assertThrows(InvalidUrlException.class, () -> {
            urlValidator.isValidUrl("dsdada");
        });
    }
}