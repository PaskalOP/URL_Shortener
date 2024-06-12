package com.example.URL_Shortener;

import com.example.URL_Shortener.security.data.UserDetailsImpl;
import com.example.URL_Shortener.shorter.data.entity.EntityURL;
import com.example.URL_Shortener.shorter.data.responseDTO.ResponseURLStatDTO;
import com.example.URL_Shortener.shorter.data.responseDTO.ResponseURLStatDTOForMVC;
import com.example.URL_Shortener.shorter.exceptions.InvalidUrlException;
import com.example.URL_Shortener.shorter.service.CreatorShortURL;
import com.example.URL_Shortener.shorter.service.ValidInputData;
import com.example.URL_Shortener.shorter.utils.mapper.Mapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MapperTest {

    @Mock
    private CreatorShortURL creatorShortURL;

    @Mock
    private ValidInputData validInputData;

    @InjectMocks
    private Mapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMapFromURLToEntity() {
        String originURL = "http://example.com";
        String login = "user";

        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(userDetails.getUsername()).thenReturn(login);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        EntityURL expectedEntity = new EntityURL();
        when(creatorShortURL.createShortURL(originURL, login)).thenReturn(expectedEntity);

        EntityURL result = mapper.mapFromURLToEntity(originURL);

        assertEquals(expectedEntity, result);
    }

    @Test
    void testMapFromListEntityToListResponseURLStatDTO() {
        EntityURL entity1 = new EntityURL();
        entity1.setShortURL("short1");
        entity1.setCountUse(10L);

        EntityURL entity2 = new EntityURL();
        entity2.setShortURL("short2");
        entity2.setCountUse(20L);

        List<EntityURL> entityURLList = Arrays.asList(entity1, entity2);

        List<ResponseURLStatDTO> result = mapper.mapFromListEntityToListResponseURLStatDTO(entityURLList);

        assertEquals(2, result.size());
        assertEquals("short1", result.get(0).getURL());
        assertEquals(10L, result.get(0).getCountUse());
        assertEquals("short2", result.get(1).getURL());
        assertEquals(20L, result.get(1).getCountUse());
    }

    @Test
    void testMapFromListEntityToListResponseURLStatDTOForMVC() {
        EntityURL entity1 = new EntityURL();
        entity1.setShortURL("short1");
        entity1.setOriginURL("http://example1.com");
        entity1.setCountUse(10L);

        EntityURL entity2 = new EntityURL();
        entity2.setShortURL("short2");
        entity2.setOriginURL("http://example2.com");
        entity2.setCountUse(20L);

        List<EntityURL> entityURLList = Arrays.asList(entity1, entity2);

        List<ResponseURLStatDTOForMVC> result = mapper.mapFromListEntityToListResponseURLStatDTOForMVC(entityURLList);

        assertEquals(2, result.size());
        assertEquals("short1", result.get(0).getShortURL());
        assertEquals("http://example1.com", result.get(0).getURL());
        assertEquals(10L, result.get(0).getCountUse());
        assertEquals("short2", result.get(1).getShortURL());
        assertEquals("http://example2.com", result.get(1).getURL());
        assertEquals(20L, result.get(1).getCountUse());
    }

    @Test
    void testMapFromStringToEntity() throws JsonProcessingException {
        String jsonData = "{\"originURL\":\"http://example.com\",\"login\":\"user\",\"creatingDate\":\"2024-01-01\",\"finishDate\":\"2024-12-31\"}";
        EntityURL entityForEdit = new EntityURL();

        when(validInputData.validOriginalUrl("http://example.com")).thenReturn(true);
        when(validInputData.validLogin("user")).thenReturn(true);
        when(validInputData.validData("2024-01-01")).thenReturn(true);
        when(validInputData.validData("2024-12-31")).thenReturn(true);

        EntityURL result = mapper.mapFromStringToEntity(jsonData, entityForEdit);

        assertEquals("http://example.com", result.getOriginURL());
        assertEquals("user", result.getLogin());
        assertEquals(LocalDate.of(2024, 1, 1), result.getCreatingDate());
        assertEquals(LocalDate.of(2024, 12, 31), result.getFinishDate());
    }

    @Test
    void testMapFromStringToEntity_InvalidJson() {
        String jsonData = "invalid_json";
        EntityURL entityForEdit = new EntityURL();

        assertThrows(InvalidUrlException.class, () -> mapper.mapFromStringToEntity(jsonData, entityForEdit));
    }

    @Test
    void testMapFromStringToEntity_InvalidFieldChange() {
        String jsonData = "{\"countUse\":\"5\"}";
        EntityURL entityForEdit = new EntityURL();

        InvalidUrlException exception = assertThrows(InvalidUrlException.class, () -> mapper.mapFromStringToEntity(jsonData, entityForEdit));
        assertEquals("You can't change this param.It is automatic one", exception.getMessage());
    }
}


