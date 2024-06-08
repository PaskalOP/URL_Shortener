package com.example.URL_Shortener.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseURLStatDTOForMVC {

        private String shortURL;

        private String URL;

        private Long countUse;

}
