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
        if (entityURL == null) {
            throw new IllegalArgumentException("URL cannot be null");
        }
        return repositoryURL.save(entityURL);
    }

    @Override
    public EntityURL findByShortURL(String shortURL) {
        if (shortURL == null) {
            throw new IllegalArgumentException("URL cannot be null");
        }
        return repositoryURL.findByShortURL(shortURL);
    }

    @Override
    public EntityURL updateShortURL(EntityURL entityURL) {

        if (entityURL == null) {
            throw new IllegalArgumentException("URL cannot be null");
        }
        return repositoryURL.save(entityURL);
    }

    @Override
    public List<EntityURL> activeURL() {
        LocalDate today = LocalDate.now();
        return repositoryURL.activeURL(today);
    }

    @Override

    public void deleteURL(String shortURL) {
        if (shortURL == null) {
            throw new IllegalArgumentException("URL cannot be null");
        }
        repositoryURL.deleteURL(shortURL);
    }

    @Override
    public void increaseCount(String shortURL) {
        if (shortURL == null) {
            throw new IllegalArgumentException("URL cannot be null");
        }
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
        throw new NonActiveUrlException("The URL isn`t active", shortURL);
    }

    @Override
    public boolean deleteByShortURL(String shortURL) {
        int deletedCount = repositoryURL.deleteByShortURL(shortURL);
        return deletedCount > 0;
    }

}
