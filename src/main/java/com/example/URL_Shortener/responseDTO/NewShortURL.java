package com.example.URL_Shortener.responseDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewShortURL {

    private String originURL;
    private String shortURL;
    private Long countUse;
    private LocalDate creatingDate;
    private LocalDate finishingDate;


}
