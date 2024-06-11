package com.example.URL_Shortener.shorter.utils.mapper;


import com.example.URL_Shortener.security.data.UserDetailsImpl;
import com.example.URL_Shortener.shorter.data.entity.EntityURL;
import com.example.URL_Shortener.shorter.data.responseDTO.ResponseURLStatDTO;
import com.example.URL_Shortener.shorter.data.responseDTO.ResponseURLStatDTOForMVC;

import com.example.URL_Shortener.shorter.exceptions.InvalidUrlException;
import com.example.URL_Shortener.shorter.service.CreatorShortURL;
import com.example.URL_Shortener.shorter.service.ValidInputData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class Mapper {
    @Autowired private  CreatorShortURL creatorShortURL;

    @Autowired private ValidInputData validInputData;

    public EntityURL mapFromURLToEntity(String originURL) {
        if (originURL == null) {
            throw new IllegalArgumentException("Origin URL cannot be null");
        }
        SecurityContext context = SecurityContextHolder.getContext();
        UserDetailsImpl authentication = (UserDetailsImpl) context.getAuthentication().getPrincipal();
        String login =  authentication.getUsername();

        return creatorShortURL.createShortURL(originURL,login);
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


//    public EntityURL mapFromNewShortURLToEntity(NewShortURL newShortURL, EntityURL entityURL) {
//        if (newShortURL == null) {
//            throw new IllegalArgumentException("NewShortURL cannot be null");
//        }
//        entityURL.setShortURL(newShortURL.getShortURL());
//        entityURL.setOriginURL(newShortURL.getOriginURL());
//        entityURL.setCountUse(newShortURL.getCountUse());
//       // entityURL.setUserID(UUID.randomUUID()); - взяти з токена
//        entityURL.setCreatingDate(newShortURL.getCreatingDate());
//        entityURL.setFinishDate(newShortURL.getFinishingDate());
//
//        return entityURL;
//
//    }


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
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,String> fields = new HashMap<>();
        try {
            fields = objectMapper.readValue(jsonData, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new InvalidUrlException("Incorrect json formate",jsonData);
        }
        for (Map.Entry<String,String> item: fields.entrySet()) {
            if(item.getKey().equals("originURL")
                    && validInputData.validOriginalUrl(item.getValue())) entityForEdit.setOriginURL(item.getValue());
            if(item.getKey().equals("shortURL")
                    &&validInputData.validShortUrl(item.getValue())) entityForEdit.setShortURL(item.getValue());
            if(item.getKey().equals("countUse")) throw new InvalidUrlException("You can't change this param.It is automatic one",item.getValue());
            if(item.getKey().equals("login")
                    &&(validInputData.validLogin(item.getValue()))) entityForEdit.setLogin(item.getValue());
            if(item.getKey().equals("creatingDate")
                    &&validInputData.validData(item.getValue())) entityForEdit.setCreatingDate(LocalDate.parse(item.getValue()));
            if(item.getKey().equals("finishDate")
                    &&validInputData.validData(item.getValue())) entityForEdit.setFinishDate(LocalDate.parse(item.getValue()));
        }
        return entityForEdit;
    }
}