package com.example.URL_Shortener.service;

import com.example.URL_Shortener.entity.EntityURL;

import java.util.List;

public interface URLService {

    List<EntityURL> getAllURLs();
    EntityURL addShortURL(EntityURL entityURL);
    EntityURL findByShortURL(String shortURL);
    EntityURL updateShortURL(EntityURL entityURL);
    List<EntityURL> activeURL();
    boolean deleteURL(String shortURL);
    void increaseCount(String shortURL);

}
