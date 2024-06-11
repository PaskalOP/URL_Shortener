package com.example.URL_Shortener;

import com.example.URL_Shortener.config.Config;
import com.example.URL_Shortener.shorter.data.entity.EntityURL;
import com.example.URL_Shortener.shorter.service.CreatorShortURL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatorShortURLTests {

 @Mock
 private Config config;

 @InjectMocks
 private CreatorShortURL creatorShortURL;

 @BeforeEach
 void setUp() {
  reset(config);
  when(config.getValidityDays()).thenReturn(30);
 }

 @Test
 void testCreateShortURL() {
  String originURL = "https://example.com";
  String login = "user";

  EntityURL result = creatorShortURL.createShortURL(originURL, login);

  assertNotNull(result);
  assertEquals(originURL, result.getOriginURL());
  assertTrue(result.getShortURL().startsWith("http://localhost:9999/"));
  assertEquals(0L, result.getCountUse());
  assertEquals(login, result.getLogin());
  assertEquals(LocalDate.now(), result.getCreatingDate());
  assertEquals(LocalDate.now().plusDays(30), result.getFinishDate());
 }
}