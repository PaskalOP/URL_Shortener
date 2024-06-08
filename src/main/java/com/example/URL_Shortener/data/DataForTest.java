package com.example.URL_Shortener.data;

import com.example.URL_Shortener.entity.EntityURL;
import com.example.URL_Shortener.responseDTO.ResponseURLStatDTO;

import java.time.LocalDate;
import java.util.UUID;

public class DataForTest {

    public static EntityURL first = new EntityURL("www.google17777778.com",
                "www.gog789789789.com",
                2L, UUID.randomUUID(),
                LocalDate.now(),
                LocalDate.of(2024, 6, 6)
        );

        public static EntityURL second = new EntityURL("www.google1778.com",
                "www.gog7899.com",
                2L, UUID.randomUUID(),
                LocalDate.now(),
                LocalDate.of(2024, 6, 4)
        );

        public static ResponseURLStatDTO firstDto = new ResponseURLStatDTO("www.google17777778.com", 1L);
        public static ResponseURLStatDTO secondDto = new ResponseURLStatDTO("www.google1778.com", 1L);
}
