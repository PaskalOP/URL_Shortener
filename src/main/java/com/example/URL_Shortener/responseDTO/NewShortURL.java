package com.example.URL_Shortener.responseDTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

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
