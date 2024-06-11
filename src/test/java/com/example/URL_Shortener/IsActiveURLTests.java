package com.example.URL_Shortener;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import com.example.URL_Shortener.shorter.data.entity.EntityURL;
import com.example.URL_Shortener.shorter.repositoryService.RepositoryURL;
import com.example.URL_Shortener.shorter.repositoryService.URLServiceImpl;
import com.example.URL_Shortener.shorter.exceptions.NonActiveUrlException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IsActiveURLTests {

    @Mock
    private RepositoryURL urlRepository;

    @InjectMocks
    private URLServiceImpl urlService;

    private EntityURL entityURL;

    @BeforeEach
    void setUp() {
        entityURL = new EntityURL();
        entityURL.setShortURL("https://ex.com");
        entityURL.setOriginURL("https://example.com");
    }

    @Test
    void testIsActiveURL_whenURLIsActive() {
        entityURL.setFinishDate(LocalDate.now().plusDays(1));

        when(urlRepository.findByShortURL("https://ex.com")).thenReturn(entityURL);

        String originURL = urlService.isActiveURL("https://ex.com");

        assertEquals("https://example.com", originURL);
        verify(urlRepository, times(1)).increaseCount("https://ex.com");
    }

    @Test
    void testIsActiveURL_whenURLExpiresToday() {
        entityURL.setFinishDate(LocalDate.now());

        when(urlRepository.findByShortURL("https://ex.com")).thenReturn(entityURL);

        String originURL = urlService.isActiveURL("https://ex.com");

        assertEquals("https://example.com", originURL);
        verify(urlRepository, times(1)).increaseCount("https://ex.com");
    }

    @Test
    void testIsActiveURL_whenURLIsExpired() {
        entityURL.setFinishDate(LocalDate.now().minusDays(1));

        when(urlRepository.findByShortURL("https://ex.com")).thenReturn(entityURL);

        NonActiveUrlException exception = assertThrows(NonActiveUrlException.class, () -> {
            urlService.isActiveURL("https://ex.com");
        });

        assertEquals("The url isn`t active: https://ex.com", exception.getMessage());
        verify(urlRepository, never()).increaseCount(anyString());
    }
}
