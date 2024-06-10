package com.example.URL_Shortener.service;

import com.example.URL_Shortener.entity.EntityURL;
import com.example.URL_Shortener.service.exceptions.InvalidUrlException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ValidInputDataTest {

    @Mock
    private UrlValidator urlValidator;

    @Mock
    private UserService userService;

    @Mock
    private URLServiceImpl urlService;

    @InjectMocks
    private ValidInputData validInputData;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidShortUrl_ValidUrl_ReturnsTrue() throws InvalidUrlException {
        String shortUrl = "validShortUrl";

        when(urlValidator.isValidShortUrl(shortUrl)).thenReturn(true);
        when(urlService.findByShortURL(shortUrl)).thenThrow(new InvalidUrlException("Not found", shortUrl));

        assertTrue(validInputData.validShortUrl(shortUrl));
    }

    @Test
    public void testValidShortUrl_InvalidUrl_ThrowsException() {
        String shortUrl = "invalidShortUrl";

        when(urlValidator.isValidShortUrl(shortUrl)).thenReturn(false);

        InvalidUrlException exception = assertThrows(InvalidUrlException.class, () -> {
            validInputData.validShortUrl(shortUrl);
        });

        assertEquals("Short URL is not valid", exception.getMessage());
    }

    @Test
    public void testValidShortUrl_UrlAlreadyExists_ThrowsException() throws InvalidUrlException {
        String shortUrl = "existingShortUrl";

        when(urlValidator.isValidShortUrl(shortUrl)).thenReturn(true);
        when(urlService.findByShortURL(shortUrl)).thenReturn(new EntityURL());

        InvalidUrlException exception = assertThrows(InvalidUrlException.class, () -> {
            validInputData.validShortUrl(shortUrl);
        });

        assertEquals("Short URL is not valid", exception.getMessage());
    }

    @Test
    public void testValidOriginalUrl_ValidUrl_ReturnsTrue() {
        String originalUrl = "http://validurl.com";

        when(urlValidator.isValidUrl(originalUrl)).thenReturn(true);

        assertTrue(validInputData.validOriginalUrl(originalUrl));
    }

    @Test
    public void testValidOriginalUrl_InvalidUrl_ThrowsException() {
        String originalUrl = "invalidUrl";

        when(urlValidator.isValidUrl(originalUrl)).thenReturn(false);

        InvalidUrlException exception = assertThrows(InvalidUrlException.class, () -> {
            validInputData.validOriginalUrl(originalUrl);
        });

        assertEquals("Original URL is not valid", exception.getMessage());
    }

    @Test
    public void testValidLogin_UserExists_ReturnsTrue() {
        String login = "existingUser";

        when(userService.findUserByLogin(login)).thenReturn(new Object()); // Замініть на відповідний об'єкт користувача

        assertTrue(validInputData.validLogin(login));
    }

    @Test
    public void testValidLogin_UserDoesNotExist_ThrowsException() {
        String login = "nonExistingUser";

        when(userService.findUserByLogin(login)).thenReturn(null);

        InvalidUrlException exception = assertThrows(InvalidUrlException.class, () -> {
            validInputData.validLogin(login);
        });

        assertEquals("This user is not exist", exception.getMessage());
    }

    @Test
    public void testValidData_ValidDate_ReturnsTrue() {
        String date = "2024-12-31";

        assertTrue(validInputData.validData(date));
    }

    @Test
    public void testValidData_InvalidDateFormat_ThrowsException() {
        String date = "31-12-2024";

        InvalidUrlException exception = assertThrows(InvalidUrlException.class, () -> {
            validInputData.validData(date);
        });

        assertEquals("Invalid date format", exception.getMessage());
    }

    @Test
    public void testValidData_PastDate_ThrowsException() {
        String date = "2023-12-31";

        InvalidUrlException exception = assertThrows(InvalidUrlException.class, () -> {
            validInputData.validData(date);
        });

        assertEquals("Date can't be in the past", exception.getMessage());
    }
}
