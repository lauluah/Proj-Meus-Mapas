package com.maps.meusmapass.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleGlobalException(Exception e) {
        Error error = new Error();
        error.setMensagem("Ocorreu um erro inesperado.");
        error.setCodigoErro("GEN-500");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(MapNotFoundException.class)
    public ResponseEntity<Error> handleMapNotFoundException(MapNotFoundException e) {
        Error error = new Error();
        error.setMensagem(e.getMessage());
        error.setCodigoErro("MAP-404");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(PontoNotFoundException.class)
    public ResponseEntity<Error> handlePontoNotFoundException(PontoNotFoundException e) {
        Error error = new Error();
        error.setMensagem(e.getMessage());
        error.setCodigoErro("PONT-404");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
}
