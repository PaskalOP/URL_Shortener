package com.example.URL_Shortener.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Config {
    public String serverPort = "";
    public Config(@Value("${server.port}") String serverPort) {
        this.serverPort = serverPort;
    }
}
