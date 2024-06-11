package com.example.URL_Shortener.shorter.data.responseDTO;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseURLStatDTO {

    private String URL;
    private Long countUse;
}
