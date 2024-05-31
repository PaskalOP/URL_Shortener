package com.example.URL_Shortener.responseDTO;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseURLStatDTO {

    @NotNull
    private String URL;

    @NotNull
    private Long countUse;
}
