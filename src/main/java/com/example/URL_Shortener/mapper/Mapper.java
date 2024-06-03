package com.example.URL_Shortener.mapper;

import com.example.URL_Shortener.entity.EntityURL;
import com.example.URL_Shortener.responseDTO.NewShortURL;
import com.example.URL_Shortener.responseDTO.ResponseURLStatDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Клас Mapper для перетворення між різними об'єктами, пов'язаними з URL.
 */
public class Mapper {

    /**
     * Мапінг з даного оригінального URL рядка в об'єкт EntityURL.
     *
     * @param originURL Оригінальний URL, який потрібно замапити.
     * @return Об'єкт EntityURL з встановленим полем originURL.
     * @throws IllegalArgumentException якщо originURL дорівнює null.
     */
    public EntityURL mapFromURLToEntity(String originURL){
        if (originURL == null) {
            throw new IllegalArgumentException("Origin URL cannot be null");
        }

        EntityURL entity = new EntityURL();
        entity.setOriginURL(originURL);
        return entity;
    }

    /**
     * Мапінг з об'єкта EntityURL в об'єкт NewShortURL.
     *
     * @param entityURL Об'єкт EntityURL, який потрібно замапити.
     * @return Об'єкт NewShortURL з встановленим полем shortURL.
     * @throws IllegalArgumentException якщо entityURL дорівнює null.
     */
    public NewShortURL mapFromEntityToNewShortURL(EntityURL entityURL){
        if (entityURL == null) {
            throw new IllegalArgumentException("EntityURL cannot be null");
        }
        NewShortURL newShortURL = new NewShortURL();
        newShortURL.setShortURL(entityURL.getShortURL());
        return newShortURL;
    }

    /**
     * Мапінг зі списку об'єктів EntityURL в список об'єктів ResponseURLStatDTO.
     *
     * @param entityURLList Список об'єктів EntityURL, які потрібно замапити.
     * @return Список об'єктів ResponseURLStatDTO.
     */
    public List<ResponseURLStatDTO> mapFromListEntityToListResponseURLStatDTO(List<EntityURL> entityURLList) {
        return entityURLList.stream()
                .map(this::mapEntityURLToResponseURLStatDTO)
                .collect(Collectors.toList());
    }

    /**
     * Мапінг з одного об'єкта EntityURL в об'єкт ResponseURLStatDTO.
     *
     * @param entityURL Об'єкт EntityURL, який потрібно замапити.
     * @return Об'єкт ResponseURLStatDTO з встановленими полями URL і countUse.
     */
    private ResponseURLStatDTO mapEntityURLToResponseURLStatDTO(EntityURL entityURL) {
        ResponseURLStatDTO dto = new ResponseURLStatDTO();
        dto.setURL(entityURL.getShortURL());
        dto.setCountUse(entityURL.getCountUse());
        return dto;
    }

    /**
     * Мапінг з об'єкта NewShortURL в об'єкт EntityURL.
     *
     * @param newShortURL Об'єкт NewShortURL, який потрібно замапити.
     * @return Об'єкт EntityURL з встановленим полем shortURL.
     */
    public EntityURL mapFromNewShortURLToEntity(NewShortURL newShortURL){
        EntityURL entityURL = new EntityURL();
        entityURL.setShortURL(newShortURL.getShortURL());
        return  entityURL;
    }



}
