package com.example.URL_Shortener.service;

import com.example.URL_Shortener.security.repository.UserService;
import com.example.URL_Shortener.shorter.data.entity.EntityURL;
import com.example.URL_Shortener.shorter.exceptions.InvalidUrlException;
import com.example.URL_Shortener.shorter.repositoryService.URLServiceImpl;
import com.example.URL_Shortener.shorter.service.UrlValidator;
import com.example.URL_Shortener.shorter.service.ValidInputData;
import com.example.URL_Shortener.security.data.dto.UserDataDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ValidInputDataTest {


    private ValidInputData validInputData;
    private UrlValidator urlValidator;
    private UserService userService;
    private URLServiceImpl urlService;

    @BeforeEach
    void setUp() {
        urlValidator = Mockito.mock(UrlValidator.class);
        userService = Mockito.mock(UserService.class);
        urlService = Mockito.mock(URLServiceImpl.class);
        validInputData = new ValidInputData(urlValidator, userService, urlService);
    }

    @Test
    void testValidShortUrl_ValidUrl() {
        Mockito.when(urlValidator.isValidShortUrl("validShortUrl")).thenReturn(true);
        Mockito.when(urlService.findByShortURL("validShortUrl")).thenThrow(new InvalidUrlException("Not found", "validShortUrl"));

        assertTrue(validInputData.validShortUrl("validShortUrl"));
    }

    @Test
    void testValidShortUrl_InvalidUrl() {
        Mockito.when(urlValidator.isValidShortUrl("invalidShortUrl")).thenReturn(false);

        assertThrows(InvalidUrlException.class, () -> validInputData.validShortUrl("invalidShortUrl"));
    }

    @Test
    void testValidShortUrl_ExistingUrl() {
        EntityURL entityURL = new EntityURL();
        Mockito.when(urlValidator.isValidShortUrl("existingShortUrl")).thenReturn(true);
        Mockito.when(urlService.findByShortURL("existingShortUrl")).thenReturn(entityURL);

        assertThrows(InvalidUrlException.class, () -> validInputData.validShortUrl("existingShortUrl"));
    }

    @Test
    void testValidOriginalUrl_ValidUrl() {
        Mockito.when(urlValidator.isValidUrl("http://valid.url")).thenReturn(true);

        assertTrue(validInputData.validOriginalUrl("http://valid.url"));
    }

    @Test
    void testValidOriginalUrl_InvalidUrl() {
        Mockito.when(urlValidator.isValidUrl("invalidUrl")).thenReturn(false);

        assertThrows(InvalidUrlException.class, () -> validInputData.validOriginalUrl("invalidUrl"));
    }

    @Test
    void testValidLogin_ExistingUser() {
        UserDataDto mockUser = Mockito.mock(UserDataDto.class);
        Mockito.when(userService.findUserByLogin("existingUser")).thenReturn(mockUser);

        assertTrue(validInputData.validLogin("existingUser"));
    }

    @Test
    void testValidLogin_NonExistingUser() {
        Mockito.when(userService.findUserByLogin("nonExistingUser")).thenReturn(null);

        assertThrows(InvalidUrlException.class, () -> validInputData.validLogin("nonExistingUser"));
    }

    @Test
    void testValidData_ValidDate() {
        String validDate = LocalDate.now().plusDays(1).toString();

        assertTrue(validInputData.validData(validDate));
    }

    @Test
    void testValidData_PastDate() {
        String pastDate = LocalDate.now().minusDays(1).toString();

        assertThrows(InvalidUrlException.class, () -> validInputData.validData(pastDate));
    }

    @Test
    void testValidData_InvalidFormat() {
        String invalidDate = "invalid-date";

        assertThrows(InvalidUrlException.class, () -> validInputData.validData(invalidDate));
    }
}
