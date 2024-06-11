package com.example.URL_Shortener.shorter.repositoryService;

import com.example.URL_Shortener.shorter.data.entity.EntityURL;

import java.util.List;

public interface URLService {

    List<EntityURL> getAllURLs();
    EntityURL addShortURL(EntityURL entityURL);
    EntityURL findByShortURL(String shortURL);
    EntityURL updateShortURL(EntityURL entityURL);
    List<EntityURL> activeURL();
    void deleteURL(String shortURL);
    void increaseCount(String shortURL);
    EntityURL increaseValue(EntityURL entityURL);
    String isActiveURL(String shortURL);
    boolean deleteByShortURL(String shortURL);
}
