package com.example.URL_Shortener.responseDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class JwtResponce {

        private String token;
        private UUID id;
        private String login;

        public JwtResponce(String token, UUID id, String login) {
            this.token = token;
            this.id = id;
            this.login = login;
    }
}
