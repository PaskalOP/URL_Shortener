package com.example.URL_Shortener;

import com.example.URL_Shortener.entity.EntityURL;
import com.example.URL_Shortener.mapper.Mapper;
import com.example.URL_Shortener.responseDTO.ResponseURLStatDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

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
        // assertNotNull(entity.getUserID());
        assertNotNull(entity.getCreatingDate());
        assertNotNull(entity.getFinishDate());
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
}