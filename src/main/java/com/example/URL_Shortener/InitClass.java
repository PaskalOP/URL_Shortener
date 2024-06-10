package com.example.URL_Shortener;

import com.example.URL_Shortener.entity.EntityURL;
import com.example.URL_Shortener.service.URLServiceImpl;
import com.example.URL_Shortener.service.UrlValidator;
import com.example.URL_Shortener.service.exceptions.InvalidUrlException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class InitClass {
    @Autowired
    private URLServiceImpl service;
    @Autowired
    private UrlValidator validator;
    @PostConstruct
    public void init () throws InvalidUrlException {
        ObjectMapper n = new ObjectMapper();
        EntityURL entity = new EntityURL();
        entity.setOriginURL("bla-bla");
        entity.setShortURL("lam");
        entity.setCountUse(0L);
        entity.setLogin("Olha");
        entity.setFinishDate(LocalDate.of(2024,06,01));
        entity.setCreatingDate(LocalDate.now());
       // service.addShortURL(entity);
       // System.out.println(service.findByShortURL("la").getID());
        //service.deleteURL("la");
//        List<EntityURL> activeURL = service.activeURL();
//        for (EntityURL item :activeURL) {
//            System.out.println(item.getID());
//
//        }
        List<EntityURL> allURL = service.getAllURLs();
        for (EntityURL item :allURL) {
            System.out.println(item.getID());

        }



    }
}
