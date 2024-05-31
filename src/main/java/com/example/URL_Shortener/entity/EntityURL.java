package com.example.URL_Shortener.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EntityURL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @NotNull
    private String originURL;

    @NotNull
    private String shortURL;

    @NotNull
    private Long countUse;

    @NotNull
    private UUID UserID;

    @NotNull
    private LocalDate creatingDate;

    private LocalDate finishDate;
}