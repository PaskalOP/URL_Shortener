package com.example.URL_Shortener;

import com.example.URL_Shortener.controller.URLController;
import com.example.URL_Shortener.entity.EntityURL;
import com.example.URL_Shortener.mapper.Mapper;
import com.example.URL_Shortener.responseDTO.NewShortURL;
import com.example.URL_Shortener.responseDTO.ResponseURLStatDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MapperTest {

    private Mapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new Mapper();
    }

    @Test
    public void testMapFromURLToEntity() {
        String originURL = "http://example.com";
        EntityURL entity = mapper.mapFromURLToEntity(originURL);
        assertNotNull(entity);
        assertEquals(originURL, entity.getOriginURL());
        assertNotNull(entity.getShortURL());
        assertNotNull(entity.getCountUse());
        assertNotNull(entity.getUserID());
        assertNotNull(entity.getCreatingDate());
        assertNotNull(entity.getFinishDate());
    }

    @Test
    public void testMapFromEntityToNewShortURL() {
        EntityURL entityURL = new EntityURL();
        entityURL.setOriginURL("http://original.url");
        entityURL.setShortURL("http://short.url");
        entityURL.setCountUse(5L);
        entityURL.setCreatingDate(LocalDate.of(2024, 6, 1));
        entityURL.setFinishDate(LocalDate.of(2024, 6, 2));

        NewShortURL newShortURL = mapper.mapFromEntityToNewShortURL(entityURL);

        assertNotNull(newShortURL);
        assertEquals("http://original.url", newShortURL.getOriginURL());
        assertEquals("http://short.url", newShortURL.getShortURL());
        assertEquals(5L, newShortURL.getCountUse());
        assertEquals(LocalDate.of(2024, 6, 1), newShortURL.getCreatingDate());
        assertEquals(LocalDate.of(2024, 6, 2), newShortURL.getFinishingDate());
    }

    @Test
    public void testMapFromListEntityToListResponseURLStatDTO() {
        EntityURL entity1 = new EntityURL();
        entity1.setShortURL("http://short1.com");
        entity1.setCountUse(10L);

        EntityURL entity2 = new EntityURL();
        entity2.setShortURL("http://short2.com");
        entity2.setCountUse(20L);

        List<EntityURL> entityList = Arrays.asList(entity1, entity2);

        List<ResponseURLStatDTO> dtoList = mapper.mapFromListEntityToListResponseURLStatDTO(entityList);
        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());

        ResponseURLStatDTO dto1 = dtoList.get(0);
        assertEquals(entity1.getShortURL(), dto1.getURL());
        assertEquals(entity1.getCountUse(), dto1.getCountUse());

        ResponseURLStatDTO dto2 = dtoList.get(1);
        assertEquals(entity2.getShortURL(), dto2.getURL());
        assertEquals(entity2.getCountUse(), dto2.getCountUse());
    }

    @Test
    public void testMapFromNewShortURLToEntity() {
        // Create a NewShortURL object with some fields set to null
        NewShortURL newShortURL = new NewShortURL();
        newShortURL.setShortURL("http://short.com");
        newShortURL.setOriginURL("http://original.com");
        newShortURL.setCountUse(0L);
        newShortURL.setCreatingDate(LocalDate.now());
        newShortURL.setFinishingDate(LocalDate.now());

        EntityURL entityURL = new EntityURL();
        entityURL.setOriginURL("http://example.com");
        entityURL.setShortURL("http://short.url/abc123");
        entityURL.setCountUse(10L);
        entityURL.setCreatingDate(LocalDate.of(2023,03,04) );
        entityURL.setCreatingDate(LocalDate.of(2023,03,04) );

        EntityURL editedEntityURL = mapper.mapFromNewShortURLToEntity(newShortURL,entityURL);

        // Assertions
        assertNotNull(entityURL);
        assertEquals("http://short.com", entityURL.getShortURL());
        assertEquals("http://original.com", entityURL.getOriginURL());
        assertEquals(Long.valueOf(0), entityURL.getCountUse());
        assertNotNull(entityURL.getUserID());
        assertNotNull(entityURL.getCreatingDate());
        assertNotNull(entityURL.getFinishDate());
    }
}