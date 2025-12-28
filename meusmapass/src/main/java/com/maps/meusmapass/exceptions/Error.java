package com.maps.meusmapass.exceptions;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
public class Error {
    private String mensagem;
    private String codigoErro;
    private LocalDateTime dataHora;

    public Error() {
        this.dataHora = LocalDateTime.now();
    }

    public Error(String mensagem, String codigoErro) {
        this.mensagem = mensagem;
        this.codigoErro = codigoErro;
    }
}
