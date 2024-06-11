package com.example.URL_Shortener;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.URL_Shortener.shorter.controller.URLController;
import com.example.URL_Shortener.shorter.repositoryService.URLServiceImpl;
import com.example.URL_Shortener.shorter.utils.mapper.Mapper;
import com.example.URL_Shortener.shorter.service.UrlValidator;
import com.example.URL_Shortener.shorter.data.entity.EntityURL;
import com.example.URL_Shortener.shorter.data.responseDTO.ResponseURLStatDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class URLControllerTests {

    private URLServiceImpl service = mock(URLServiceImpl.class);
    private Mapper mapper = mock(Mapper.class);
    private UrlValidator validator = mock(UrlValidator.class);
    private URLController controller;

    @BeforeEach
    public void setUp() {
        controller = new URLController(mapper, service, validator);
    }

    @Test
    public void testCreateShortUrl_Success() throws Exception {
        // Arrange
        String originalUrl = "http://example.com";
        String shortUrl = "http://short.url/abc123";

        EntityURL entityURL = new EntityURL();
        entityURL.setShortURL(shortUrl);

        when(mapper.mapFromURLToEntity(originalUrl)).thenReturn(entityURL);

        // Act
        ResponseEntity<String> response = controller.createShortUrl(originalUrl);

        // Assert
        verify(validator).isValidUrl(originalUrl);
        verify(mapper).mapFromURLToEntity(originalUrl);
        verify(service).addShortURL(entityURL);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(shortUrl, response.getBody());
    }


    @Test
    public void testEditObject_Success() throws Exception {
        // Arrange
        String jsonString = "{\"url\": \"http://new.example.com\"}";
        String shortUrl = "http://short.url/abc123";

        EntityURL entityForEdit = new EntityURL();
        EntityURL editedEntity = new EntityURL();

        when(service.findByShortURL(shortUrl)).thenReturn(entityForEdit);
        when(mapper.mapFromStringToEntity(jsonString, entityForEdit)).thenReturn(editedEntity);

        // Act
        HttpStatus status = controller.editObject(jsonString, shortUrl);

        // Assert
        verify(service).findByShortURL(shortUrl);
        verify(mapper).mapFromStringToEntity(jsonString, entityForEdit);
        verify(service).updateShortURL(editedEntity);
        assertEquals(HttpStatus.OK, status);
    }


    @Test
    public void testActiveUrls_Success() {
        // Arrange
        List<EntityURL> urls = new ArrayList<>(); // Add some sample URLs to the list
        List<ResponseURLStatDTO> mappedResponse = new ArrayList<>(); // Add some sample mapped response

        when(service.activeURL()).thenReturn(urls);
        when(mapper.mapFromListEntityToListResponseURLStatDTO(urls)).thenReturn(mappedResponse);

        // Act
        ResponseEntity<List<ResponseURLStatDTO>> response = controller.activeUrls();

        // Assert
        verify(service).activeURL();
        verify(mapper).mapFromListEntityToListResponseURLStatDTO(urls);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mappedResponse, response.getBody());
    }

    @Test
    public void testAllUrls_Success() {
        // Arrange
        List<EntityURL> urls = new ArrayList<>(); // Add some sample URLs to the list
        List<ResponseURLStatDTO> mappedResponse = new ArrayList<>(); // Add some sample mapped response

        when(service.getAllURLs()).thenReturn(urls);
        when(mapper.mapFromListEntityToListResponseURLStatDTO(urls)).thenReturn(mappedResponse);

        // Act
        ResponseEntity<List<ResponseURLStatDTO>> response = controller.allUrls();

        // Assert
        verify(service).getAllURLs();
        verify(mapper).mapFromListEntityToListResponseURLStatDTO(urls);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mappedResponse, response.getBody());
    }

    @Test
    public void testDeleteUrl_Success() throws IllegalArgumentException {
        // Arrange
        String shortUrl = "http://short.url/abc123";

        doNothing().when(service).deleteURL(shortUrl);

        // Act
        ResponseEntity<String> response = controller.deleteUrl(shortUrl);

        // Assert
        verify(service).deleteURL(shortUrl);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted", response.getBody());
    }
}
