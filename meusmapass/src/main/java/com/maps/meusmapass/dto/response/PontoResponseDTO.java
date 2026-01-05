package com.maps.meusmapass.dto.response;
import com.maps.meusmapass.model.Ponto;
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

    public PontoResponseDTO() {
    }

    public PontoResponseDTO(Ponto ponto) {
        this.id = ponto.getId();
        this.nome = ponto.getNome();
        this.descricao = ponto.getDescricao();
        this.latitude = ponto.getLatitude();
        this.longitude = ponto.getLongitude();
    }
}


