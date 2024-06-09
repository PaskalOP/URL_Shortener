package com.example.URL_Shortener.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/api/V2/shorter")
public class About {
    @GetMapping("/about")
    public ModelAndView getAboutPage() {
        return new ModelAndView("about");
    }

}
