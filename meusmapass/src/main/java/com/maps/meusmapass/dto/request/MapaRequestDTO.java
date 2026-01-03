package com.maps.meusmapass.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapaRequestDTO {

    @NotBlank(message = "Nome do mapa é obrigatório")
    private String nome;
}
