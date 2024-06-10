package com.example.URL_Shortener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.example.URL_Shortener.controller.URLController;
import com.example.URL_Shortener.data.DataForTest;
import com.example.URL_Shortener.mapper.Mapper;
import com.example.URL_Shortener.responseDTO.ResponseURLStatDTO;
import com.example.URL_Shortener.service.URLServiceImpl;
import com.example.URL_Shortener.service.UrlValidator;

import com.example.URL_Shortener.entity.EntityURL;
import com.example.URL_Shortener.service.exceptions.InvalidUrlException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class URLControllerTests {

    @Mock
    private URLServiceImpl service = mock(URLServiceImpl.class);
    @Mock
    private UrlValidator validator = mock(UrlValidator.class);
    @Mock
    private Mapper mapper = mock(Mapper.class);
    @Mock
    private URLController controller = new URLController(mapper, service, validator);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        controller = new URLController(mapper, service, validator);
    }

    @Test
    public void testCreateShortUrl_Success() throws InvalidUrlException {
        // Arrange
        String originalUrl = "http://example.com";
        String shortUrl = "http://short.com/abc123";

        EntityURL entityURL = new EntityURL();
        entityURL.setOriginURL(originalUrl);
        entityURL.setShortURL(shortUrl);

        when(validator.isValidUrl(originalUrl)).thenReturn(true);
        when(mapper.mapFromURLToEntity(originalUrl)).thenReturn(entityURL);

        // Act
        ResponseEntity<String> response = controller.createShortUrl(originalUrl);

        // Assert
        verify(validator).isValidUrl(originalUrl);
        verify(mapper).mapFromURLToEntity(originalUrl);
        verify(service).addShortURL(entityURL);
        assert response.getStatusCode() == HttpStatus.OK;
        assert Objects.equals(response.getBody(), shortUrl);
    }

    @Test
    public void testEditFullObject_Success() throws IllegalArgumentException {
        // Arrange
        String shortUrl = "http://short.com/abc123";
        String newData = "http://shortener.com/abc123";
        EntityURL entityForEdit = new EntityURL();
        EntityURL editedEntity = new EntityURL();

        when(service.findByShortURL(shortUrl)).thenReturn(entityForEdit);
        when(mapper.mapFromStringToEntity(newData, entityForEdit)).thenReturn(editedEntity);

        // Act
        HttpStatus status = controller.editObject(newData, shortUrl);

        // Assert
        verify(service).findByShortURL(shortUrl);
       // verify(mapper).mapFromURLToEntity(newData, entityForEdit);
        verify(service).updateShortURL(editedEntity);
        assert status == HttpStatus.OK;
    }

    @Test
    public void testActiveUrls_Success() {
        // Arrange
        List<EntityURL> urls = new ArrayList<>();
        urls.add(DataForTest.first);
        urls.add(DataForTest.second);
        List<ResponseURLStatDTO> mappedResponse = new ArrayList<>();
        mappedResponse.add(DataForTest.firstDto);
        mappedResponse.add(DataForTest.secondDto);

        when(service.activeURL()).thenReturn(urls);
        when(mapper.mapFromListEntityToListResponseURLStatDTO(urls)).thenReturn(mappedResponse);

        // Act
        ResponseEntity<List<ResponseURLStatDTO>> response = controller.activeUrls();

        // Assert
        verify(service).activeURL();
        verify(mapper).mapFromListEntityToListResponseURLStatDTO(urls);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() == mappedResponse;
    }

    @Test
    public void testAllUrls_Success() {
        // Arrange
        List<EntityURL> urls = new ArrayList<>();
        urls.add(DataForTest.first);
        urls.add(DataForTest.second);
        List<ResponseURLStatDTO> mappedResponse = new ArrayList<>();
        mappedResponse.add(DataForTest.firstDto);
        mappedResponse.add(DataForTest.secondDto);

        when(service.getAllURLs()).thenReturn(urls);
        when(mapper.mapFromListEntityToListResponseURLStatDTO(urls)).thenReturn(mappedResponse);

        // Act
        ResponseEntity<List<ResponseURLStatDTO>> response = controller.allUrls();

        // Assert
        verify(service).getAllURLs();
        verify(mapper).mapFromListEntityToListResponseURLStatDTO(urls);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() == mappedResponse;
    }

    @Test
    public void testDeleteUrl_Success() throws IllegalArgumentException {
        // Arrange
        String shortUrl = "http://short.com/abc123";

        // Act
        ResponseEntity<String> response = controller.deleteUrl(shortUrl);

        // Assert
        verify(service).deleteURL(shortUrl);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals("Deleted");
    }


    @Test
    public void testMapFromStringToEntity_CountUseUpdated() {
        // Мокові дані у форматі JSON
        String jsonData = "{\"countUse\": 10}";

        // Створення мокового об'єкта EntityURL для редагування
        EntityURL entityForEdit = new EntityURL();
        entityForEdit.setCountUse(5L);

        // Виклик методу для тестування
        Mapper mapper = new Mapper();
        EntityURL resultEntity = mapper.mapFromStringToEntity(jsonData, entityForEdit);

        // Перевірка
        assertEquals(10, resultEntity.getCountUse());
    }
}