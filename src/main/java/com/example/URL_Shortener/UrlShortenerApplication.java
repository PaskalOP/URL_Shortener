package com.example.URL_Shortener;

import com.example.URL_Shortener.entity.NewShortURL;
import com.example.URL_Shortener.service.CreatorShortURL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UrlShortenerApplication implements CommandLineRunner {

    @Autowired
    private CreatorShortURL createShortURL;

    public static void main(String[] args) {
        SpringApplication.run(UrlShortenerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // Виклик сервісу для створення короткої URL
        NewShortURL shortURL = createShortURL.createShortURL("https://goit.global/ua/");
        System.out.println(shortURL);
    }
}