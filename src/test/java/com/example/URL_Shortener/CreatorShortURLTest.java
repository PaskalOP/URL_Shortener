package com.example.URL_Shortener;

import com.example.URL_Shortener.CreatorShortURL;
import com.example.URL_Shortener.entity.NewShortURL;
import com.example.URL_Shortener.responseDTO.ResponseInvalidDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CreatorShortURLTest {

    @Test
    void createShortURL_ValidURL_ReturnsNewShortURLObject() {
        // Arrange
        CreatorShortURL creatorShortURL = new CreatorShortURL();
        CreatorShortURL.OriginURL originURL = new CreatorShortURL.OriginURL();
        originURL.setOriginalURL("https://goit.global/ua/");

        // Act
        Object result = creatorShortURL.createShortURL(originURL);

        // Assert
        assertTrue(result instanceof NewShortURL);
        NewShortURL newShortURL = (NewShortURL) result;
        assertNotNull(newShortURL.getShortURL());
        assertNotNull(newShortURL.getCreatingDate());
        assertNotNull(newShortURL.getFinishingDate());
    }


    @Test
    void createShortURL_InvalidURL_ReturnsResponseInvalidDTO() {    // не виконується тест падає
        // Arrange
        CreatorShortURL creatorShortURL = new CreatorShortURL();
        CreatorShortURL.OriginURL originURL = new CreatorShortURL.OriginURL();
        originURL.setOriginalURL("invalid-url");

        // Act
        Object result = creatorShortURL.createShortURL(originURL);

        // Assert
        assertTrue(result instanceof ResponseInvalidDTO);
        ResponseInvalidDTO responseInvalidDTO = (ResponseInvalidDTO) result;
        assertEquals("invalid-url", responseInvalidDTO.getMessage());
    }
}