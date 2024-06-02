package com.example.URL_Shortener;

import com.example.URL_Shortener.entity.EntityURL;
import com.example.URL_Shortener.entity.NewShortURL;
import com.example.URL_Shortener.mapper.Mapper;
import com.example.URL_Shortener.responseDTO.ResponseURLStatDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MapperTest {

    private Mapper mapper;

    /**
     * Налаштування перед кожним тестом.
     */
    @BeforeEach
    public void setUp() {
        mapper = new Mapper();
    }

    /**
     * Тест для методу mapFromURLToEntity.
     * Перевіряє, чи правильно мапиться оригінальний URL в об'єкт EntityURL.
     */
    @Test
    public void testMapFromURLToEntity() {
        String originURL = "https://example.com";
        EntityURL entityURL = mapper.mapFromURLToEntity(originURL);
        assertNotNull(entityURL); // Перевірка, що об'єкт не null
        assertEquals(originURL, entityURL.getOriginURL()); // Перевірка, що поле originURL встановлено правильно
    }

    /**
     * Тест для методу mapFromEntityToNewShortURL.
     * Перевіряє, чи правильно мапиться об'єкт EntityURL в об'єкт NewShortURL.
     */
    @Test
    public void testMapFromEntityToNewShortURL() {
        EntityURL entityURL = new EntityURL();
        entityURL.setShortURL("https://exampleshort.com");
        NewShortURL newShortURL = mapper.mapFromEntityToNewShortURL(entityURL);
        assertNotNull(newShortURL); // Перевірка, що об'єкт не null
        assertEquals(entityURL.getShortURL(), newShortURL.getShortURL()); // Перевірка, що поле shortURL встановлено правильно
    }

    /**
     * Тест для методу mapFromListEntityToListResponseURLStatDTO.
     * Перевіряє, чи правильно мапиться список об'єктів EntityURL в список об'єктів ResponseURLStatDTO.
     */
    @Test
    public void testMapFromListEntityToListResponseURLStatDTO() {
        List<EntityURL> entityURLList = new ArrayList<>();

        // Створення та налаштування першого EntityURL
        EntityURL entityURL1 = new EntityURL();
        entityURL1.setShortURL("short1");
        entityURL1.setCountUse(10L);
        entityURLList.add(entityURL1);

        // Створення та налаштування другого EntityURL
        EntityURL entityURL2 = new EntityURL();
        entityURL2.setShortURL("short2");
        entityURL2.setCountUse(20L);
        entityURLList.add(entityURL2);

        // Мапінг списку EntityURL в список ResponseURLStatDTO
        List<ResponseURLStatDTO> responseURLStatDTOList = mapper.mapFromListEntityToListResponseURLStatDTO(entityURLList);
        assertNotNull(responseURLStatDTOList); // Перевірка, що список не null
        assertEquals(2, responseURLStatDTOList.size()); // Перевірка, що розмір списку правильний
        assertEquals(entityURL1.getShortURL(), responseURLStatDTOList.get(0).getURL()); // Перевірка першого елемента
        assertEquals(entityURL1.getCountUse(), responseURLStatDTOList.get(0).getCountUse()); // Перевірка першого елемента
        assertEquals(entityURL2.getShortURL(), responseURLStatDTOList.get(1).getURL()); // Перевірка другого елемента
        assertEquals(entityURL2.getCountUse(), responseURLStatDTOList.get(1).getCountUse()); // Перевірка другого елемента
    }

    /**
     * Тест для методу mapFromNewShortURLToEntity.
     * Перевіряє, чи правильно мапиться об'єкт NewShortURL в об'єкт EntityURL.
     */
    @Test
    public void testMapFromNewShortURLToEntity() {
        NewShortURL newShortURL = new NewShortURL();
        newShortURL.setShortURL("short");
        EntityURL entityURL = mapper.mapFromNewShortURLToEntity(newShortURL);
        assertNotNull(entityURL); // Перевірка, що об'єкт не null
        assertEquals(newShortURL.getShortURL(), entityURL.getShortURL()); // Перевірка, що поле shortURL встановлено правильно
    }
}
