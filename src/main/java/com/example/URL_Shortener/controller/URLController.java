package com.example.URL_Shortener.controller;

import com.example.URL_Shortener.entity.EntityURL;
import com.example.URL_Shortener.mapper.Mapper;
import com.example.URL_Shortener.responseDTO.NewShortURL;
import com.example.URL_Shortener.responseDTO.ResponseURLStatDTO;
import com.example.URL_Shortener.service.URLServiceImpl;
import com.example.URL_Shortener.service.UrlValidator;
import com.example.URL_Shortener.service.exceptions.InvalidUrlException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/V1/shorter")
public class URLController {
    private final Mapper mapper;
    private final URLServiceImpl service;
    private final UrlValidator validator;

    @GetMapping("/create")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<String> createShortUrl(@RequestParam String originalUrl) throws InvalidUrlException{
        validator.isValidUrl(originalUrl);
        EntityURL entityURL = mapper.mapFromURLToEntity(originalUrl);
        service.addShortURL(entityURL);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entityURL.getShortURL());
    }
    @PostMapping("/edit")
    @SecurityRequirement(name = "Bearer Authentication")
    public HttpStatus editObject(@RequestBody String jsonString, @RequestParam String shortUrl) throws InvalidUrlException{
        EntityURL entityForEdit = service.findByShortURL(shortUrl);
        EntityURL editedEntity = mapper.mapFromStringToEntity(jsonString,entityForEdit);
        service.updateShortURL(editedEntity);
        return HttpStatus.OK;
    }

    @GetMapping("/active")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<ResponseURLStatDTO>> activeUrls(){
        List<EntityURL> urls = service.activeURL();
        List<ResponseURLStatDTO> response = mapper.mapFromListEntityToListResponseURLStatDTO(urls);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/all")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<ResponseURLStatDTO>> allUrls(){
        List<EntityURL> urls = service.getAllURLs();
        List<ResponseURLStatDTO> response = mapper.mapFromListEntityToListResponseURLStatDTO(urls);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<String>  deleteUrl(@RequestParam String shortURL) throws IllegalArgumentException  {
        service.deleteURL(shortURL);
        return  ResponseEntity.status(HttpStatus.OK).body("Deleted");
    }
}
