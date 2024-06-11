package com.example.URL_Shortener.shorter.utils;

import com.example.URL_Shortener.shorter.data.entity.EntityURL;
import com.example.URL_Shortener.shorter.data.responseDTO.ResponseURLStatDTO;

import java.time.LocalDate;

public class DataForTest {

    public static EntityURL first = new EntityURL("www.google17777778.com",
                "www.gog789789789.com",
                2L, "login",
                LocalDate.now(),
                LocalDate.of(2024, 6, 6)
        );

        public static EntityURL second = new EntityURL("www.google1778.com",
                "www.gog7899.com",
                2L, "logon",
                LocalDate.now(),
                LocalDate.of(2024, 6, 4)
        );

        public static ResponseURLStatDTO firstDto = new ResponseURLStatDTO("www.google17777778.com", 1L);
        public static ResponseURLStatDTO secondDto = new ResponseURLStatDTO("www.google1778.com", 1L);
}
