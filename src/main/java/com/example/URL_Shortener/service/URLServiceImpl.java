package com.example.URL_Shortener.service;

import com.example.URL_Shortener.config.MainExceptionHandler;
import com.example.URL_Shortener.entity.EntityURL;
import com.example.URL_Shortener.repository.RepositoryURL;
import com.example.URL_Shortener.service.exceptions.InvalidUrlException;
import com.example.URL_Shortener.service.exceptions.NonActiveUrlException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


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
    @Cacheable({"findByShortURLs", "shortURLs"})
    public EntityURL findByShortURL(String shortURL) {
        if (shortURL == null) {
            throw new IllegalArgumentException("URL cannot be null");
        }
        return Optional.ofNullable(repositoryURL.findByShortURL(shortURL))
                .orElseThrow(() -> new InvalidUrlException("The URL isn`t found", shortURL));
    }

    @Override
    public EntityURL updateShortURL(EntityURL entityURL) {

        if (entityURL == null) {
            throw new IllegalArgumentException("URL cannot be null");
        }
        return repositoryURL.save(entityURL);
    }

    @Override
    @Cacheable("activeURLs")
    public List<EntityURL> activeURL() {
        LocalDate today = LocalDate.now();
        return repositoryURL.activeURL(today);
    }

    @Override

    public void deleteURL(String shortURL) {
        if (shortURL == null) {
            throw new IllegalArgumentException("URL cannot be null");
        }
        Optional<EntityURL> optionalEntityURL = Optional.ofNullable(repositoryURL.findByShortURL(shortURL));
        if (optionalEntityURL.isPresent()){
            repositoryURL.deleteURL(shortURL);
        }else {
            throw new InvalidUrlException("The URL isn`t found", shortURL);
        }

    }

    @Override
    public void increaseCount(String shortURL) {
        if (shortURL == null) {
            throw new IllegalArgumentException("URL cannot be null");
        }
        repositoryURL.increaseCount(shortURL);
    }

    @Override
    public EntityURL increaseValue(EntityURL entityURL) {
        if (entityURL == null){
            throw new IllegalArgumentException("URL cannot be null");
        }
        entityURL.setCountUse(Objects.requireNonNullElse(entityURL.getCountUse(), 0L)+1);
        return repositoryURL.save(entityURL);
    }

    @Override
    public String isActiveURL(String shortURL) {
        EntityURL entityByShortURL = findByShortURL(shortURL);
        LocalDate today = LocalDate.now();

        if (entityByShortURL.getFinishDate().isAfter(today) || entityByShortURL.getFinishDate().isEqual(today)) {
            entityByShortURL.setCountUse( entityByShortURL.getCountUse()+1);
            repositoryURL.save(entityByShortURL);
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
