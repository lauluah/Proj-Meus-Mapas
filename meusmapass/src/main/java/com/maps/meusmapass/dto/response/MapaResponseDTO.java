package com.maps.meusmapass.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class MapaResponseDTO {

    private Long id;
    private String nome;
    private LocalDateTime dataCriacao;
}
