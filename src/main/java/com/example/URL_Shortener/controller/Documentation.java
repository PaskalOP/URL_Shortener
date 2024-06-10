package com.example.URL_Shortener.controller;

import com.example.URL_Shortener.config.SwaggerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/api/V2/shorter")
public class Documentation {

    @Autowired
    private SwaggerProperties swaggerProperties;

    @GetMapping("/restapidoc")
    public String getDocumentPage(Model model) {
        System.out.println("Swagger URL: " + swaggerProperties.getUrl());
        model.addAttribute("swaggerUrl", swaggerProperties.getUrl());
        return "index";
    }
}
