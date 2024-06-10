package com.example.URL_Shortener.service;

import com.example.URL_Shortener.entity.EntityURL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

public class ValidInputDataTest {

    // Тест на оновлення короткого URL з правильною URL
    @Test
    public void testUpdateShortUrl_ValidShortUrl() {
        // Створення мокового UrlValidator
        UrlValidator urlValidator = Mockito.mock(UrlValidator.class);
        Mockito.when(urlValidator.isValidShortUrl(Mockito.anyString())).thenReturn(true);

        // Створення об'єкта ValidInputData з моковим UrlValidator
       // ValidInputData validInputData = new ValidInputData(urlValidator);

        // Початкові дані
        EntityURL entityForEdit = new EntityURL();
        entityForEdit.setShortURL("http://old.short.com/123");

        // Нові дані для оновлення
        EntityURL tempEntity = new EntityURL();
        tempEntity.setShortURL("http://short.com/123");

        // Виклик методу для тестування
       // validInputData.updateShortUrl(entityForEdit, tempEntity);

        // Перевірка результату
        Assertions.assertEquals("http://short.com/123", entityForEdit.getShortURL());
    }

    // Тест на оновлення короткого URL з неправильною URL
    @Test
    public void testUpdateShortUrl_InvalidShortUrl() {
        // Створення мокового UrlValidator
        UrlValidator urlValidator = Mockito.mock(UrlValidator.class);
        Mockito.when(urlValidator.isValidShortUrl(Mockito.anyString())).thenReturn(false);

        // Створення об'єкта ValidInputData з моковим UrlValidator
       // ValidInputData validInputData = new ValidInputData(urlValidator);

        // Початкові дані
        EntityURL entityForEdit = new EntityURL();
        entityForEdit.setShortURL("http://old.short.com/123");

        // Нові дані для оновлення з неправильною URL
        EntityURL tempEntity = new EntityURL();
        tempEntity.setShortURL("invalid_url");

        // Перевірка, що метод кидає виняток IllegalArgumentException
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
           // validInputData.updateShortUrl(entityForEdit, tempEntity);
        });
    }

    // Тест на оновлення кількості використань з від'ємним значенням
    @Test
    public void testUpdateCountUse_NegativeCountUse() {
        UrlValidator urlValidator = new UrlValidator();
        //ValidInputData validInputData = new ValidInputData(urlValidator);

        // Початкові дані
        EntityURL entityForEdit = new EntityURL();
        entityForEdit.setCountUse(5L);

        // Нові дані для оновлення з від'ємним значенням
        EntityURL tempEntity = new EntityURL();
        tempEntity.setCountUse(-1L);

        // Перевірка, що метод кидає виняток IllegalArgumentException
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
          //  validInputData.updateCountUse(entityForEdit, tempEntity);
        });
    }

    // Тест на оновлення дати створення з майбутньою датою
    @Test
    public void testUpdateCreatingDate_FutureCreatingDate() {
        UrlValidator urlValidator = new UrlValidator();
       // ValidInputData validInputData = new ValidInputData(urlValidator);

        // Початкові дані
        EntityURL entityForEdit = new EntityURL();
        entityForEdit.setCreatingDate(LocalDate.now());

        // Нові дані для оновлення з майбутньою датою
        EntityURL tempEntity = new EntityURL();
        tempEntity.setCreatingDate(LocalDate.now().plusDays(1));

        // Перевірка, що метод кидає виняток IllegalArgumentException
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
           // validInputData.updateCreatingDate(entityForEdit, tempEntity);
        });
    }

    // Тест на оновлення дати закінчення з минулою датою
    @Test
    public void testUpdateFinishDate_PastFinishDate() {
        UrlValidator urlValidator = new UrlValidator();
       // ValidInputData validInputData = new ValidInputData(urlValidator);

        // Початкові дані
        EntityURL entityForEdit = new EntityURL();
        entityForEdit.setFinishDate(LocalDate.now());

        // Нові дані для оновлення з минулою датою
        EntityURL tempEntity = new EntityURL();
        tempEntity.setFinishDate(LocalDate.now().minusDays(1));

        // Перевірка, що метод кидає виняток IllegalArgumentException
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
          //  validInputData.updateFinishDate(entityForEdit, tempEntity);
        });
    }
}
