package com.maps.meusmapass.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;


@Getter
@Setter
public class PontoRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 20, message = "O nome deve ter entre 3 e 20 caracteres.")
    private String nome;

    private String descricao;

    @NotNull(message = "Latitude é obrigatória")
    private Double latitude;

    @NotNull(message = "Longitude é obrigatória")
    private Double longitude;

}
