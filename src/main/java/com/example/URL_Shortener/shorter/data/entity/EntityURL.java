package com.example.URL_Shortener.shorter.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Getter
@Setter
@Table(name = "urls")
public class EntityURL {

    @Id
    private Long ID;
    @Column
    private String originURL;
    @Column
    private String shortURL;
    @Column (name = "countuse")
    private Long countUse;
    @Column
    private String login;
    @Column (name = "creatingdate")
    private LocalDate creatingDate;
    @Column (name = "finishdate")
    private LocalDate finishDate;

    public EntityURL(){
        this.ID = UUID.randomUUID().getLeastSignificantBits()*(-1);
    }

    public EntityURL(String originURL, String shortURL, Long countUse, String login, LocalDate creatingDate, LocalDate finishDate) {
        this.ID = UUID.randomUUID().getLeastSignificantBits()*(-1);
        this.originURL = originURL;
        this.shortURL = shortURL;
        this.countUse = countUse;
        this.login = login;
        this.creatingDate = creatingDate;
        this.finishDate = finishDate;
    }
}