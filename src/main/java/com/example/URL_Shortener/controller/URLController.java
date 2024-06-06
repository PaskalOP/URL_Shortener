package com.example.URL_Shortener.controller;

import com.example.URL_Shortener.entity.EntityURL;
import com.example.URL_Shortener.mapper.Mapper;
import com.example.URL_Shortener.responseDTO.NewShortURL;
import com.example.URL_Shortener.responseDTO.ResponseURLStatDTO;
import com.example.URL_Shortener.service.CreatorShortURL;
import com.example.URL_Shortener.service.URLServiceImpl;
import com.example.URL_Shortener.service.UrlValidator;
import com.example.URL_Shortener.service.exceptions.InvalidUrlException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<NewShortURL> createShortUrl(@RequestParam String originalUrl){
        try {
           if(validator.isValidUrl(originalUrl)){
               EntityURL entityURL = mapper.mapFromURLToEntity(originalUrl);
               NewShortURL response = mapper.mapFromEntityToNewShortURL(entityURL);
               service.addShortURL(entityURL);
               return ResponseEntity
                       .status(HttpStatus.OK)
                       .body(response);
           } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (InvalidUrlException e) {
            throw new IllegalArgumentException("Invalid url");
        }
    }
    @PostMapping("/editFull")
    public HttpStatus editFullObject (@RequestBody NewShortURL data, @RequestParam String shortUrl){
        try {
            EntityURL entityForEdit = service.findByShortURL(shortUrl);
            EntityURL editedEntity = mapper.mapFromNewShortURLToEntity(data,entityForEdit);
            service.updateShortURL(editedEntity);
            return HttpStatus.OK;
        } catch (IllegalArgumentException e){
            return HttpStatus.BAD_REQUEST;
        }
    }
    @GetMapping("/active")
    public ResponseEntity<List<ResponseURLStatDTO>> activeUrls(){
        List<EntityURL> urls = service.activeURL();
        List<ResponseURLStatDTO> response = mapper.mapFromListEntityToListResponseURLStatDTO(urls);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/all")
    public ResponseEntity<List<ResponseURLStatDTO>> allUrls(){
        List<EntityURL> urls = service.getAllURLs();
        List<ResponseURLStatDTO> response = mapper.mapFromListEntityToListResponseURLStatDTO(urls);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete")
    public HttpStatus deleteUrl(@RequestParam String shortURL){
        if(service.deleteURL(shortURL)) return HttpStatus.OK;
        else return HttpStatus.BAD_REQUEST;
    }


}
