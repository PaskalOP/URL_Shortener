package com.example.URL_Shortener;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestRedirectController {
    @RequestMapping("/Redirect")
    public @ResponseBody String greeting() {
        return "Hello, Redirect";
    }
}
