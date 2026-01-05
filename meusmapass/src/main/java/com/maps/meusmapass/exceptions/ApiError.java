package com.maps.meusmapass.exceptions;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
public class ApiError {
    private String mensagem;
    private String codigoErro;
    private LocalDateTime dataHora;

    public ApiError() {
        this.dataHora = LocalDateTime.now();
    }

    public ApiError(String mensagem, String codigoErro) {
        this.mensagem = mensagem;
        this.codigoErro = codigoErro;
    }
}
