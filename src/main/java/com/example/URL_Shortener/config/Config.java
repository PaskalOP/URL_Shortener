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
    public String jwtSecret;
    public Long jwtExpiration;

    public Config(@Value("${server.port}") String serverPort,
                  @Value("${JWT_SECRET}") String jwtSecret,
                  @Value("${JWT_EXPIRATION}") Long jwtExpiration)
    {
        this.serverPort = serverPort;
        this.jwtSecret = jwtSecret;
        this.jwtExpiration = jwtExpiration;
    }
}
