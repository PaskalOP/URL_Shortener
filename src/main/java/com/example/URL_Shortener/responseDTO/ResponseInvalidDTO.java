package com.example.URL_Shortener.responseDTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseInvalidDTO {

    private String inputedURL;
    private String message;

    public ResponseInvalidDTO(String message) {
        this.message = message;
    }
}