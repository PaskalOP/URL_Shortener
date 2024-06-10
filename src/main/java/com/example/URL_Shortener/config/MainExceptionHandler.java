package com.example.URL_Shortener.config;

import com.example.URL_Shortener.responseDTO.ResponseInvalidDTO;
import com.example.URL_Shortener.service.exceptions.InvalidUrlException;
import com.example.URL_Shortener.service.exceptions.NonActiveUrlException;
import com.example.URL_Shortener.service.exceptions.UserAlreadyExistException;
import com.example.URL_Shortener.service.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Головний обробник виключень для додатка
 * Цей клас визначає глобальні хендлери виключень для обробки специфічних виключень,
 * таких як InvalidUrlException, і повертає відповідні відповіді клієнту з відповідним HTTP статусом.
 */
@RestControllerAdvice
public class MainExceptionHandler {

    /**
     * Обробляє виключення типу InvalidUrlException.
     * Коли виникає виключення InvalidUrlException, цей метод створює і заповнює
     * об'єкт ResponseInvalidDTO з повідомленням про помилку та невалідним URL, і
     * повертає його клієнту з HTTP статусом 400 (BAD_REQUEST).
     *
     * @param ex виключення InvalidUrlException, що містить повідомлення про помилку та невалідний URL.
     * @return ResponseEntity з об'єктом ResponseInvalidDTO і HTTP статусом 400.
     */
    @ExceptionHandler(value = {InvalidUrlException.class})
    public ResponseEntity<ResponseInvalidDTO> invalidUrlException(InvalidUrlException ex) {
        ResponseInvalidDTO invalidDto = new ResponseInvalidDTO();
        invalidDto.setInvalidURL(ex.getInvalidUrl());
        invalidDto.setMessage(Collections.singletonList(ex.getMessage()).toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(invalidDto);
    }

    /**
     * Обробляє виключення типу NonActiveUrlException.
     * Коли виникає виключення NonActiveUrlException, цей метод створює і заповнює
     * об'єкт ResponseInvalidDTO з повідомленням про помилку та неактивним URL, і
     * повертає його клієнту з HTTP статусом 404 (NOT_FOUND).
     *
     * @param ex виключення NonActiveUrlException, що містить повідомлення про помилку та неактивний URL.
     * @return ResponseEntity з об'єктом ResponseInvalidDTO і HTTP статусом 404.
     */
    @ExceptionHandler(value = {NonActiveUrlException.class})
    public ResponseEntity<ResponseInvalidDTO> nonActiveUrlException(NonActiveUrlException ex) {
        ResponseInvalidDTO invalidDto = new ResponseInvalidDTO();
        invalidDto.setInvalidURL(ex.getInvalidUrl());
        invalidDto.setMessage(Collections.singletonList(ex.getMessage()).toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(invalidDto);
    }

    /**
     * Обробник винятків для користувацьких винятків UserAlreadyExistException і UserNotFoundException.
     *
     * @param ex Exception - об'єкт винятку
     * @return ResponseEntity<Map<String, List<String>>> - відповідь з повідомленням про помилку та статусом BAD_REQUEST
     */
    @ExceptionHandler(value = {
            UserAlreadyExistException.class,
            UserNotFoundException.class
    })
    public ResponseEntity<Map<String, List<String>>> conflictException(Exception ex) {
        Map<String, List<String>> map = new HashMap<>();
        map.put("errors", Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обробляє всі інші виключення типу Exception.
     * Коли виникає будь-яке інше виключення типу Exception, цей метод створює мапу,
     * що містить список з повідомленнями про помилки, і повертає її клієнту з HTTP статусом 400 (BAD_REQUEST).
     *
     * @param ex виключення типу Exception, що містить повідомлення про помилку.
     * @return ResponseEntity з мапою, що містить список повідомлень про помилки, і HTTP статусом 400.
     */
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Map<String, List<String>>> anotherException(Exception ex) {
        Map<String, List<String>> map = new HashMap<>();
        map.put("errors", Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}