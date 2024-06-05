package com.example.URL_Shortener.service;

import com.example.URL_Shortener.entity.EntityURL;
import com.example.URL_Shortener.repository.RepositoryURL;
import com.example.URL_Shortener.service.exceptions.NonActiveUrlException;
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
        return repositoryURL.save(entityURL);
    }

    @Override
    public EntityURL findByShortURL(String shortURL) {
        return repositoryURL.findByShortURL(shortURL);
    }
    @Override
    public EntityURL updateShortURL(EntityURL entityURL) {
        EntityURL currentURL = repositoryURL.findByShortURL(entityURL.getShortURL());
        currentURL.setOriginURL(entityURL.getOriginURL());
        currentURL.setShortURL(entityURL.getShortURL());
        currentURL.setCountUse(entityURL.getCountUse());
        currentURL.setUserID(entityURL.getUserID());
        currentURL.setCreatingDate(LocalDate.now());
        currentURL.setFinishDate(LocalDate.now().plusDays(1));
        return currentURL;
    }

    @Override
    public List<EntityURL> activeURL() {
        LocalDate today = LocalDate.now();
        return repositoryURL.activeURL(today);
    }

    @Override
    public boolean deleteURL(String shortURL) {
        return repositoryURL.deleteURL(shortURL);
    }

    @Override
    public void increaseCount(String shortURL) {
        repositoryURL.increaseCount(shortURL);
    }

    @Override
    public String isActiveURL(String shortURL) {
        EntityURL entityByShortURL = findByShortURL(shortURL);
        LocalDate today = LocalDate.now();
            if (entityByShortURL.getFinishDate().isAfter(today) || entityByShortURL.getFinishDate().isEqual(today)) {
                increaseCount(shortURL);
                return entityByShortURL.getOriginURL();
            }
        throw new NonActiveUrlException("The url isn`t active: " + shortURL);
    }
}
