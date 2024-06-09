package com.example.URL_Shortener.mapper;

import com.example.URL_Shortener.entity.EntityURL;
import com.example.URL_Shortener.responseDTO.NewShortURL;
import com.example.URL_Shortener.responseDTO.ResponseURLStatDTO;
import com.example.URL_Shortener.responseDTO.ResponseURLStatDTOForMVC;
import com.example.URL_Shortener.service.CreatorShortURL;
import com.example.URL_Shortener.service.UrlValidator;
import com.example.URL_Shortener.service.ValidInputData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class Mapper {
    private final CreatorShortURL creatorShortURL = new CreatorShortURL();

    private UrlValidator urlValidator;

    public EntityURL mapFromURLToEntity(String originURL) {
        if (originURL == null) {
            throw new IllegalArgumentException("Origin URL cannot be null");
        }

        NewShortURL newShortURL = creatorShortURL.createShortURL(originURL);

        EntityURL entity = new EntityURL();
        entity.setOriginURL(originURL);
        entity.setShortURL(newShortURL.getShortURL());
        entity.setCountUse(newShortURL.getCountUse());
        entity.setUserID(UUID.randomUUID());
        entity.setCreatingDate(newShortURL.getCreatingDate());
        entity.setFinishDate(newShortURL.getFinishingDate());
        return entity;
    }


    public NewShortURL mapFromEntityToNewShortURL(EntityURL entityURL) {
        if (entityURL == null) {
            throw new IllegalArgumentException("EntityURL cannot be null");
        }

        NewShortURL newShortURL = new NewShortURL();
        newShortURL.setOriginURL(entityURL.getOriginURL());
        newShortURL.setShortURL(entityURL.getShortURL());
        newShortURL.setCountUse(Objects.requireNonNullElse(entityURL.getCountUse(), 0L));
        newShortURL.setCreatingDate(entityURL.getCreatingDate());
        newShortURL.setFinishingDate(entityURL.getFinishDate());


        return newShortURL;
    }


    public List<ResponseURLStatDTO> mapFromListEntityToListResponseURLStatDTO(List<EntityURL> entityURLList) {
        return entityURLList.stream()
                .map(this::mapEntityURLToResponseURLStatDTO)
                .collect(Collectors.toList());
    }


    private ResponseURLStatDTO mapEntityURLToResponseURLStatDTO(EntityURL entityURL) {
        ResponseURLStatDTO dto = new ResponseURLStatDTO();
        dto.setURL(entityURL.getShortURL());
        dto.setCountUse(entityURL.getCountUse());
        return dto;
    }


    public EntityURL mapFromNewShortURLToEntity(NewShortURL newShortURL, EntityURL entityURL) {
        if (newShortURL == null) {
            throw new IllegalArgumentException("NewShortURL cannot be null");
        }
        entityURL.setShortURL(newShortURL.getShortURL());
        entityURL.setOriginURL(newShortURL.getOriginURL());
        entityURL.setCountUse(newShortURL.getCountUse());
        entityURL.setUserID(UUID.randomUUID());
        entityURL.setCreatingDate(newShortURL.getCreatingDate());
        entityURL.setFinishDate(newShortURL.getFinishingDate());

        return entityURL;

    }


    public List<ResponseURLStatDTOForMVC> mapFromListEntityToListResponseURLStatDTOForMVC(List<EntityURL> entityURLList) {
        return entityURLList.stream()
                .map(this::mapEntityURLToResponseURLStatDTOForMVC)
                .collect(Collectors.toList());
    }

    private ResponseURLStatDTOForMVC mapEntityURLToResponseURLStatDTOForMVC(EntityURL entityURL) {
        ResponseURLStatDTOForMVC dtoForMVC = new ResponseURLStatDTOForMVC();
        dtoForMVC.setShortURL(entityURL.getShortURL());
        dtoForMVC.setURL(entityURL.getOriginURL());
        dtoForMVC.setCountUse(entityURL.getCountUse());
        return dtoForMVC;
    }

    public EntityURL mapFromStringToEntity(String jsonData, EntityURL entityForEdit) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            EntityURL tempEntity = objectMapper.readValue(jsonData, EntityURL.class);

            ValidInputData updater = new ValidInputData(urlValidator);
            updater.updateShortUrl(entityForEdit, tempEntity);
            updater.updateCountUse(entityForEdit, tempEntity);
            updater.updateCreatingDate(entityForEdit, tempEntity);
            updater.updateFinishDate(entityForEdit, tempEntity);

            return entityForEdit;
        } catch (Exception e) {
            throw new RuntimeException("Failed to map JSON to entity: " + e.getMessage(), e);
        }
    }
}