package com.example.URL_Shortener.controller;

import com.example.URL_Shortener.entity.EntityURL;
import com.example.URL_Shortener.mapper.Mapper;
import com.example.URL_Shortener.responseDTO.NewShortURL;
import com.example.URL_Shortener.service.CreatorShortURL;
import com.example.URL_Shortener.service.URLServiceImpl;
import com.example.URL_Shortener.service.UrlValidator;
import com.example.URL_Shortener.service.exceptions.InvalidUrlException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/V1/shorter")
public class URLController {
    private final Mapper mapper;
    private final URLServiceImpl service;
    private final UrlValidator validator;


    @GetMapping("/create")
    public ResponseEntity<NewShortURL> createShortUrl(@RequestParam String originalUrl){
        try {
            validator.isValidUrl(originalUrl);
            EntityURL entityURL = mapper.mapFromURLToEntity(originalUrl);
            NewShortURL response = mapper.mapFromEntityToNewShortURL(entityURL);
            service.addShortURL(entityURL);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);

        } catch (InvalidUrlException e) {
            throw new IllegalArgumentException("Invalid url");
        }
    }
}
