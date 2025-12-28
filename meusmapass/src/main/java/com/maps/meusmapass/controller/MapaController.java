package com.maps.meusmapass.controller;



import com.maps.meusmapass.model.Mapa;
import com.maps.meusmapass.service.MapaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maps")
@CrossOrigin
public class MapaController {

    private final MapaService mapaService;

    public MapaController(MapaService mapaService) {
        this.mapaService = mapaService;
    }

    @GetMapping
    public List<Mapa> listar() {
        return mapaService.listarMapas();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mapa criar(@RequestBody Mapa mapa) {
        return mapaService.criarMapa(mapa);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        mapaService.excluirMapa(id);
    }

}
