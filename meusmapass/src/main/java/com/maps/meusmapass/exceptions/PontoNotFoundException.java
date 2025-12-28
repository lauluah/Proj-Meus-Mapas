package com.maps.meusmapass.exceptions;

public class PontoNotFoundException extends RuntimeException {
    public PontoNotFoundException(Long id) {
        super("Ponto não encontrado com id: " + id);
    }
}
