package com.maps.meusmapass.dto.request;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class PontoRequestDTO {

        @NotBlank(message = "Nome é obrigatório")
        private String nome;

        @NotBlank(message = "Descrição é obrigatória")
        private String descricao;

        private Double latitude;

        private Double longitude;

}
