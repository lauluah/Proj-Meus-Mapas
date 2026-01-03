package com.maps.meusmapass.controller;

import com.maps.meusmapass.dto.request.PontoRequestDTO;
import com.maps.meusmapass.dto.response.PontoResponseDTO;
import com.maps.meusmapass.model.Ponto;
import com.maps.meusmapass.service.PontoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/mapas/{mapaId}/pontos")
@CrossOrigin
public class PontoController {

    private final PontoService pontoService;

    public PontoController(PontoService pontoService) {
        this.pontoService = pontoService;
    }

    @GetMapping
    public List<PontoResponseDTO> listar(@PathVariable Long mapaId) {
        return pontoService.listarPontosPorMapa(mapaId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PontoResponseDTO criarPonto(
            @PathVariable Long mapaId,
            @Valid @RequestBody PontoRequestDTO dto
    ) {
        return pontoService.criarPonto(mapaId, dto);
    }
}

