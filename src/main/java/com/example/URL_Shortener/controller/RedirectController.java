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
public class RedirectController {

    @Autowired private URLServiceImpl service;
    @Autowired private Config config;

    @GetMapping("/{shortURL:[a-zA-Z0-9]{6,8}}")
    public ModelAndView redirectByShortURL(@PathVariable String shortURL) throws InvalidUrlException {
        String partUrl = "http://localhost:" + config.serverPort + "/" + shortURL;
        String originURL = service.isActiveURL(partUrl);
        return new ModelAndView( "redirect:"+originURL);

    }
}
