package com.maps.meusmapass.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PontoResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private Double latitude;
    private Double longitude;
}


