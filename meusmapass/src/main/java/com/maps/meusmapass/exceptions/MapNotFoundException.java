package com.maps.meusmapass.exceptions;

public class MapNotFoundException extends RuntimeException {
    public MapNotFoundException(Long id) {
        super("Mapa não encontrado com id: " + id);
    }
}
