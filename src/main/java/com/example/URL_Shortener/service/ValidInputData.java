package com.example.URL_Shortener.service;

import com.example.URL_Shortener.entity.EntityURL;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

/**
 * Клас для перевірки та оновлення вхідних даних.
 */
@RequiredArgsConstructor
public class ValidInputData {
    private final UrlValidator urlValidator;

    /**
     * Оновлює короткий URL в екземплярі EntityURL згідно з вхідними даними.
     * @param entityForEdit екземпляр EntityURL, який потрібно оновити
     * @param tempEntity тимчасовий екземпляр EntityURL з новими даними
     */
    public void updateShortUrl(EntityURL entityForEdit, EntityURL tempEntity) {
        String shortUrl = tempEntity.getShortURL();
        if (shortUrl != null) {
            if (!urlValidator.isValidShortUrl(shortUrl)) {
                throw new IllegalArgumentException("Short URL is not valid: " + shortUrl);
            }
            entityForEdit.setShortURL(shortUrl);
        }
    }

    /**
     * Оновлює кількість використань в екземплярі EntityURL згідно з вхідними даними.
     * @param entityForEdit екземпляр EntityURL, який потрібно оновити
     * @param tempEntity тимчасовий екземпляр EntityURL з новими даними
     */
    public void updateCountUse(EntityURL entityForEdit, EntityURL tempEntity) {
        Long countUse = tempEntity.getCountUse();
        if (countUse != null && countUse < 0) {
            throw new IllegalArgumentException("Count use cannot be less than 0");
        }
        entityForEdit.setCountUse(countUse);
    }

    /**
     * Оновлює дату створення в екземплярі EntityURL згідно з вхідними даними.
     * @param entityForEdit екземпляр EntityURL, який потрібно оновити
     * @param tempEntity тимчасовий екземпляр EntityURL з новими даними
     */
    public void updateCreatingDate(EntityURL entityForEdit, EntityURL tempEntity) {
        LocalDate creatingDate = tempEntity.getCreatingDate();
        if (creatingDate != null && creatingDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Creating date cannot be in the future");
        }
        entityForEdit.setCreatingDate(creatingDate);
    }

    /**
     * Оновлює дату закінчення в екземплярі EntityURL згідно з вхідними даними.
     * @param entityForEdit екземпляр EntityURL, який потрібно оновити
     * @param tempEntity тимчасовий екземпляр EntityURL з новими даними
     */
    public void updateFinishDate(EntityURL entityForEdit, EntityURL tempEntity) {
        LocalDate finishDate = tempEntity.getFinishDate();
        if (finishDate != null && finishDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Finish date cannot be in the past");
        }
        entityForEdit.setFinishDate(finishDate);
    }
}