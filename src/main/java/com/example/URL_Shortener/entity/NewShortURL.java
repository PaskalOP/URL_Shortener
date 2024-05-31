package com.example.URL_Shortener.entity;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewShortURL {

    @NotNull
    private String originURL;

    @NotNull
    private String shortURL;

    @NotNull
    private Long countUse;

    @NotNull
    private LocalDate creatingDate;

    private LocalDate finishingDate;
}