package com.example.URL_Shortener.controller;

import com.example.URL_Shortener.entity.EntityURL;
import com.example.URL_Shortener.mapper.Mapper;
import com.example.URL_Shortener.responseDTO.NewShortURL;
import com.example.URL_Shortener.responseDTO.ResponseURLStatDTO;
import com.example.URL_Shortener.responseDTO.ResponseURLStatDTOForMVC;
import com.example.URL_Shortener.service.URLServiceImpl;
import com.example.URL_Shortener.service.UrlValidator;
import com.example.URL_Shortener.service.exceptions.InvalidUrlException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/V2/shorter")
public class URLMvcController {

    private final Mapper mapper;
    private final URLServiceImpl service;
    private final UrlValidator validator;

    @PostMapping("/create")
    public ModelAndView createShortUrl(@RequestParam String originalUrl) throws InvalidUrlException {
//        validator.isValidUrl(originalUrl);


        ModelAndView modelAndView = new ModelAndView("redirect:/api/V2/shorter/all");
        EntityURL entityURL = mapper.mapFromURLToEntity(originalUrl);
        service.addShortURL(entityURL);
//        modelAndView.setStatus(HttpStatus.OK);
        return modelAndView;
    }


    @GetMapping("/active")
    public ModelAndView activeUrls() {
        List<EntityURL> urls = service.activeURL();
        List<ResponseURLStatDTOForMVC> response = mapper.mapFromListEntityToListResponseURLStatDTOForMVC(urls);
        ModelAndView modelAndView = new ModelAndView("activeUrls");
        modelAndView.addObject("urls", response);
        return modelAndView;
    }

    @GetMapping("/all")
    public ModelAndView allUrls() {
        List<EntityURL> urls = service.getAllURLs();
        List<ResponseURLStatDTOForMVC> response = mapper.mapFromListEntityToListResponseURLStatDTOForMVC(urls);
        ModelAndView modelAndView = new ModelAndView("all");
        modelAndView.addObject("urls", response);
        return modelAndView;
    }

    @PostMapping("/delete")
    public ModelAndView deleteShortURL(@RequestParam String shortURL) {
        ModelAndView modelAndView = new ModelAndView("redirect:/api/V2/shorter/all");
        try {
            System.out.println("Attempting to delete shortURL: " + shortURL);
            boolean isDeleted = service.deleteByShortURL(shortURL);
            if (isDeleted) {
                System.out.println("Successfully deleted shortURL: " + shortURL);
                return modelAndView;
            } else {
                System.out.println("No URL found for the given shortURL: " + shortURL);
                modelAndView.addObject("errors", List.of("No URL found for the given short URL."));
                return modelAndView;
            }
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("errors", List.of("Error deleting URL: " + e.getMessage()));
            return modelAndView;
        }
    }
}
