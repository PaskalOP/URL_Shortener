package com.example.URL_Shortener.responseDTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseInvalidDTO {

    private String invalidURL;
    private String message;

}