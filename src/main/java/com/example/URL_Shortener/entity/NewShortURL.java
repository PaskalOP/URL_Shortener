package com.example.URL_Shortener.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewShortURL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Ідентифікатор сутності

    @NotNull
    private String originURL;

    @NotNull
    private String shortURL;

    @NotNull
    private Long countUse;

    @NotNull
    private LocalDate creatingDate;

    private LocalDate finishingDate;

    // не знаю у мене чомусь без конструктора не пашить
    public NewShortURL(String originURL, String shortURL, LocalDate creatingDate, LocalDate finishingDate) {
        this.originURL = originURL;
        this.shortURL = shortURL;
        this.creatingDate = creatingDate;
        this.finishingDate = finishingDate;
    }
}