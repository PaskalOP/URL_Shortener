package com.example.URL_Shortener.MVC;

import com.example.URL_Shortener.shorter.data.entity.EntityURL;
import com.example.URL_Shortener.shorter.utils.mapper.Mapper;
import com.example.URL_Shortener.shorter.data.responseDTO.ResponseURLStatDTOForMVC;
import com.example.URL_Shortener.shorter.service.CreatorShortURL;
import com.example.URL_Shortener.shorter.repositoryService.URLServiceImpl;
import com.example.URL_Shortener.shorter.service.UrlValidator;
import com.example.URL_Shortener.shorter.exceptions.InvalidUrlException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/V2/shorter")
public class URLMvcController {

    private final Mapper mapper;
    private final URLServiceImpl service;
    private final UrlValidator validator;
    private final CreatorShortURL creatorShortURL;

    @PostMapping("/create")
    public ModelAndView createShortUrl(@RequestParam String originalUrl) throws InvalidUrlException {
       validator.isValidUrl(originalUrl);

        ModelAndView modelAndView = new ModelAndView("redirect:/api/V2/shorter/all");
        EntityURL entityURL = creatorShortURL.createShortURL(originalUrl,"defoult");
        service.addShortURL(entityURL);
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
//    @PostMapping("/editFull")
//    public ModelAndView editFullObject(@RequestBody String data, @RequestParam String shortUrl) throws IllegalArgumentException {
//        EntityURL entityForEdit = service.findByShortURL(shortUrl);
//        EntityURL editedEntity = mapper.mapFromStringToEntity(data, entityForEdit);
//        service.updateShortURL(editedEntity);
//        return new ModelAndView("redirect:/api/V2/shorter/all");
//    }

    @PostMapping("/editFull")
    public ModelAndView editFullObject(@RequestParam String shortURL, @RequestParam String data) throws IllegalArgumentException {

        EntityURL entityForEdit = service.findByShortURL(shortURL);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode dataNode;
        try {
            dataNode = mapper.readTree(data);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid JSON data", e);
        }

        if (dataNode.has("originURL") && !dataNode.get("originURL").asText().isEmpty()) {
            entityForEdit.setOriginURL(dataNode.get("originURL").asText());
        }
        if (dataNode.has("finishDate") && !dataNode.get("finishDate").asText().isEmpty()) {
            entityForEdit.setFinishDate(LocalDate.parse(dataNode.get("finishDate").asText()));
        }
        service.updateShortURL(entityForEdit);
        return new ModelAndView("redirect:/api/V2/shorter/all");
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
