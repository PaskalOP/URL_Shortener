package com.example.URL_Shortener.shorter.data.responseDTO;

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