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
    public Integer validityDays;

    public Config(@Value("${server.port}") String serverPort,
                  @Value("${JWT_SECRET}") String jwtSecret,
                  @Value("${JWT_EXPIRATION}") Long jwtExpiration,
                  @Value("${short.url.validity.days}") Integer validityDays){
        this.serverPort = serverPort;
        this.jwtSecret = jwtSecret;
        this.jwtExpiration = jwtExpiration;
        this.validityDays = validityDays;
    }
}
