package com.maps.meusmapass.controller;

import com.maps.meusmapass.dto.request.PontoRequestDTO;
import com.maps.meusmapass.dto.request.PontoUpdateDTO;
import com.maps.meusmapass.dto.response.PontoResponseDTO;
import com.maps.meusmapass.service.PontoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/mapas/{mapaId}/pontos")
public class PontoController {

    private final PontoService pontoService;

    public PontoController(PontoService pontoService) {
        this.pontoService = pontoService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PontoResponseDTO criarPonto(
            @PathVariable Long mapaId,
            @Valid @RequestBody PontoRequestDTO dto
    ) {
        return pontoService.criarPonto(mapaId, dto);
    }

    @GetMapping
    public List<PontoResponseDTO> listar(@PathVariable Long mapaId) {
        return pontoService.listarPontosPorMapa(mapaId);
    }

    @PutMapping("/{id}")
    public PontoResponseDTO atualizarPonto(
            @PathVariable Long mapaId,
            @PathVariable Long id,
            @Valid @RequestBody PontoUpdateDTO dto
    ) {
        return pontoService.atualizarPonto(mapaId, id, dto);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        pontoService.excluirPonto(id);
    }

}

