package com.maps.meusmapass.service;



import com.maps.meusmapass.exceptions.MapNotFoundException;
import com.maps.meusmapass.model.Mapa;
import com.maps.meusmapass.repository.MapaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MapaService {
    private final MapaRepository mapaRepository;

    public MapaService(MapaRepository mapaRepository) {
        this.mapaRepository = mapaRepository;
    }

    public List<Mapa> listarMapas() {
        return mapaRepository.findAll();
    }

    public Mapa buscarMapaPorId(Long id) {
        return mapaRepository.findById(id)
                .orElseThrow(() -> new MapNotFoundException(id));
    }

    public Mapa criarMapa(Mapa mapa) {
        mapa.setDataCriacao(LocalDateTime.now());
        return mapaRepository.save(mapa);
    }

    public void excluirMapa(Long id) {
        if (!mapaRepository.existsById(id)) {
            throw new MapNotFoundException(id);
        }
        mapaRepository.deleteById(id);
    }
}
