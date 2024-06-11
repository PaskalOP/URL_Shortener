package com.example.URL_Shortener;

import com.example.URL_Shortener.shorter.data.entity.EntityURL;
import com.example.URL_Shortener.shorter.exceptions.InvalidUrlException;
import com.example.URL_Shortener.shorter.exceptions.NonActiveUrlException;
import com.example.URL_Shortener.shorter.repositoryService.RepositoryURL;
import com.example.URL_Shortener.shorter.repositoryService.URLServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class URLServiceImplTests {

    @Mock
    private RepositoryURL repositoryURL;

    @InjectMocks
    private URLServiceImpl urlService;

    private EntityURL entityURL;

    @BeforeEach
    void setUp() {
        entityURL = new EntityURL();
        entityURL.setShortURL("https://ex.com");
        entityURL.setOriginURL("https://example.com");
        entityURL.setCountUse(0L);
    }

    @Test
    void testIsActiveURL_whenURLIsActive() {
        entityURL.setFinishDate(LocalDate.now().plusDays(1));
        when(repositoryURL.findByShortURL(anyString())).thenReturn(entityURL);

        String result = urlService.isActiveURL("https://ex.com");

        assertEquals("https://example.com", result);
        assertEquals(1, entityURL.getCountUse());
        verify(repositoryURL, times(1)).save(entityURL);
    }

    @Test
    void testIsActiveURL_whenURLExpiresToday() {
        entityURL.setFinishDate(LocalDate.now());
        when(repositoryURL.findByShortURL(anyString())).thenReturn(entityURL);

        String result = urlService.isActiveURL("https://ex.com");

        assertEquals("https://example.com", result);
        assertEquals(1, entityURL.getCountUse());
        verify(repositoryURL, times(1)).save(entityURL);
    }

    @Test
    void testIsActiveURL_whenURLIsExpired() {
        entityURL.setFinishDate(LocalDate.now().minusDays(1));
        when(repositoryURL.findByShortURL(anyString())).thenReturn(entityURL);

        NonActiveUrlException exception = assertThrows(NonActiveUrlException.class, () -> {
            urlService.isActiveURL("https://ex.com");
        });

        assertEquals("The URL isn`t active", exception.getMessage());
        verify(repositoryURL, never()).save(entityURL);
    }

    @Test
    void testIsActiveURL_whenURLIsNotFound() {
        when(repositoryURL.findByShortURL(anyString())).thenReturn(null);

        InvalidUrlException exception = assertThrows(InvalidUrlException.class, () -> {
            urlService.isActiveURL("https://ex.com");
        });

        assertEquals("The URL isn`t found", exception.getMessage());
        verify(repositoryURL, never()).save(any(EntityURL.class));
    }
}


