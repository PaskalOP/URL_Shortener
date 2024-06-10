package com.example.URL_Shortener.responseDTO;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewShortURL {

    private String originURL;
    private String shortURL;
    private Long countUse;
    private LocalDate creatingDate;
    private LocalDate finishingDate;

    public NewShortURL(String originURL, String fullShortURL, LocalDate now, LocalDate creatingDate, long l) {
    }
}
