package com.example.URL_Shortener.shorter.repositoryService;

import com.example.URL_Shortener.shorter.data.entity.EntityURL;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RepositoryURL extends JpaRepository<EntityURL, Long> {

    @Cacheable("findByShortURLs")
    @Query(nativeQuery = true, value = "SELECT * FROM urls WHERE shortURL = :shortURL")
    EntityURL findByShortURL(@Param("shortURL") String shortURL);

    @Query(nativeQuery = true, value = "SELECT * FROM urls WHERE finishDate >= :today")
    List<EntityURL> activeURL(@Param("today") LocalDate today);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM urls WHERE shortURL = :shortURL")
    void deleteURL(@Param("shortURL") String shortURL);

//    @Transactional
//    @Modifying
//    @Query(nativeQuery = true, value = "DELETE FROM urls WHERE shortURL LIKE %:shortURL%")
//    void deleteURL(@Param("shortURL") String shortURL);


    @Modifying
    @Query(nativeQuery = true, value = "UPDATE urls SET countUse = countUse + 1 WHERE shortURL = :shortURL")
    void increaseCount(@Param("shortURL") String shortURL);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM urls WHERE shortURL LIKE %:shortURL%")
    int deleteByShortURL(@Param("shortURL") String shortURL);

}
