package com.example.URL_Shortener.controller;

import com.example.URL_Shortener.config.Config;
import com.example.URL_Shortener.service.URLServiceImpl;
import com.example.URL_Shortener.service.UrlValidator;
import com.example.URL_Shortener.service.exceptions.InvalidUrlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Контролер для перенаправлення користувача за коротким URL.
 */
@Controller
@ResponseBody
@RequestMapping("/shorter")
public class RedirectController {

    @Autowired
    private URLServiceImpl service;
    @Autowired
    private UrlValidator validator;
    @Autowired
    private Config config;
    @Autowired
    private URLServiceImpl urlService;

    /**
     * Метод для перенаправлення користувача за коротким URL.
     *
     * @param shortURL String - короткий URL
     * @return ResponseEntity<?> - відповідь з перенаправленням або помилкою
     * @throws InvalidUrlException - виняток, якщо URL недійсний
     */
    @GetMapping("/{shortURL}")
    public ResponseEntity<?> redirectByShortURL(@PathVariable String shortURL) throws InvalidUrlException {
        String partUrl = "http://localhost:" + config.getServerPort() + "/shorter/" + shortURL;
        urlService.increaseCount(partUrl);
        validator.isValidShortUrl(partUrl);
        String originURL = service.findByShortURL(partUrl).getOriginURL();
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header("Location", originURL)
                .build();
    }
}
