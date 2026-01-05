package com.maps.meusmapass.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapaRequestDTO {

    @NotBlank(message = "Nome do mapa é obrigatório")
    @Size(min = 3, max = 20, message = "O nome deve ter entre 3 e 20 caracteres.")
    private String nome;
}
