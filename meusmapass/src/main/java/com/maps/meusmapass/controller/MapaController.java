package com.maps.meusmapass.controller;

import com.maps.meusmapass.dto.request.MapaRequestDTO;
import com.maps.meusmapass.dto.response.MapaResponseDTO;
import com.maps.meusmapass.service.MapaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/mapas")
public class MapaController {

    private final MapaService mapaService;

    public MapaController(MapaService mapaService) {
        this.mapaService = mapaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MapaResponseDTO criarMapa(@RequestBody @Valid MapaRequestDTO dto) {
      return mapaService.criarMapa(dto);
    }

    @GetMapping
    public List<MapaResponseDTO> listarMapas() {
        return mapaService.listarMapas();
    }

    @PutMapping("/{id}")
    public MapaResponseDTO atualizarNomeMapa(@PathVariable Long id, @Valid @RequestBody MapaRequestDTO dto) {
        return mapaService.atualizarNomeMapa(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirMapa(@PathVariable Long id) {
        mapaService.excluirMapa(id);
    }
}
