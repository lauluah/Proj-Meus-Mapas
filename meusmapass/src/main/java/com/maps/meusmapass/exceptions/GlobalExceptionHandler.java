package com.maps.meusmapass.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex) {

        List<String> erros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        return ResponseEntity.badRequest().body(
                Map.of("errors", erros)
        );
    }

    @ExceptionHandler(MapNotFoundException.class)
    public ResponseEntity<ApiError> handleMapNotFoundException(MapNotFoundException e) {
        ApiError apiError = new ApiError();
        apiError.setMensagem(e.getMessage());
        apiError.setCodigoErro("MAP-404");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(apiError);
    }

    @ExceptionHandler(PontoNotFoundException.class)
    public ResponseEntity<ApiError> handlePontoNotFoundException(PontoNotFoundException e) {
        ApiError apiError = new ApiError();
        apiError.setMensagem(e.getMessage());
        apiError.setCodigoErro("PONT-404");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(apiError);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntime(RuntimeException e) {
        ApiError apiError = new ApiError();
        apiError.setMensagem("Ocorreu um erro inesperado.");
        apiError.setCodigoErro("GEN-500");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiError);
    }
}
