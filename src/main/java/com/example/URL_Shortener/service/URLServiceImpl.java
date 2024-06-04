package com.example.URL_Shortener.service;

import com.example.URL_Shortener.entity.EntityURL;
import com.example.URL_Shortener.repository.RepositoryURL;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@RequiredArgsConstructor
@Service
public class URLServiceImpl implements URLService {

    private final RepositoryURL repositoryURL;

    @Override
    public List<EntityURL> getAllURLs() {
        return repositoryURL.findAll();
    }

    @Override
    public EntityURL addShortURL(EntityURL entityURL) {
        if (entityURL == null){
            throw new IllegalArgumentException("URL cannot be null");
        }
        return repositoryURL.save(entityURL);
    }

    @Override
    public EntityURL findByShortURL(String shortURL) {
        if (shortURL == null){
            throw new IllegalArgumentException("URL cannot be null");
        }
        return repositoryURL.findByShortURL(shortURL);
    }
    @Override
    public EntityURL updateShortURL(EntityURL entityURL) {
        if (entityURL == null){
            throw new IllegalArgumentException("URL cannot be null");
        }
        EntityURL currentURL = repositoryURL.findByShortURL(entityURL.getShortURL());
        currentURL.setOriginURL(entityURL.getOriginURL());
        currentURL.setShortURL(entityURL.getShortURL());
        currentURL.setCountUse(entityURL.getCountUse());
        currentURL.setUserID(entityURL.getUserID());
        currentURL.setCreatingDate(LocalDate.now());
        currentURL.setFinishDate(LocalDate.now().plusDays(1));
        return addShortURL(currentURL);
    }

    @Override
    public List<EntityURL> activeURL() {
        LocalDate today = LocalDate.now();
        return repositoryURL.activeURL(today);
    }

    @Override
    public boolean deleteURL(String shortURL) {
        if (shortURL == null){
            throw new IllegalArgumentException("URL cannot be null");
        }
        return repositoryURL.deleteURL(shortURL);
    }

    @Override
    public void increaseCount(String shortURL) {
        if (shortURL == null){
            throw new IllegalArgumentException("URL cannot be null");
        }
        repositoryURL.increaseCount(shortURL);
    }
}
