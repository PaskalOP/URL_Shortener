package com.example.URL_Shortener.responseDTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private LocalDate creatingDate;
    private LocalDate finishingDate;
    private Long countUse;
}