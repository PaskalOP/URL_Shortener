package com.example.URL_Shortener;

import com.example.URL_Shortener.config.Config;
import com.example.URL_Shortener.controller.RedirectController;
import com.example.URL_Shortener.service.URLServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RedirectController.class)
public class RedirectTest {

    @MockBean private URLServiceImpl service;
    @MockBean private Config config;
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void shouldReturnStatusRedirect() throws Exception {
        String shortURL = "/Redirect";
        when(service.findByShortURL(shortURL)).thenReturn(null);
        ResultActions resultActions = mockMvc.perform(get("/Redirect", shortURL))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }
}
