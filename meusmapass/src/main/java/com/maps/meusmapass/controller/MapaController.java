package com.maps.meusmapass.controller;

import com.maps.meusmapass.dto.request.MapaRequestDTO;
import com.maps.meusmapass.dto.response.MapaResponseDTO;
import com.maps.meusmapass.service.MapaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mapas")
@CrossOrigin(origins = "*")
public class MapaController {

    private final MapaService mapaService;

    public MapaController(MapaService mapaService) {
        this.mapaService = mapaService;
    }

    @PostMapping
    public ResponseEntity<MapaResponseDTO> criar(
            @RequestBody @Valid MapaRequestDTO dto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapaService.criarMapa(dto));
    }

    @GetMapping
    public List<MapaResponseDTO> listar() {
        return mapaService.listarMapas();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        mapaService.excluirMapa(id);
    }

}
