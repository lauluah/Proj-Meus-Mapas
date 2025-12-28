package com.maps.meusmapass.service;

import com.maps.meusmapass.exceptions.MapNotFoundException;
import com.maps.meusmapass.exceptions.PontoNotFoundException;
import com.maps.meusmapass.model.Mapa;
import com.maps.meusmapass.model.Ponto;
import com.maps.meusmapass.repository.MapaRepository;
import com.maps.meusmapass.repository.PontoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PontoService {

    private final PontoRepository pontoRepository;
    private final MapaRepository mapaRepository;

    public PontoService(PontoRepository pontoRepository, MapaRepository mapaRepository) {
        this.pontoRepository = pontoRepository;
        this.mapaRepository = mapaRepository;
    }

    public List<Ponto> listarPontosPorMapa(Long mapaId) {
        if (!mapaRepository.existsById(mapaId)) {
            throw new MapNotFoundException(mapaId);
        }
        return pontoRepository.findByMapaId(mapaId);
    }

    public Ponto criarPonto(Long mapaId, Ponto ponto) {
        Mapa mapa = mapaRepository.findById(mapaId)
                .orElseThrow(() -> new MapNotFoundException(mapaId));

        ponto.setMapa(mapa);
        return pontoRepository.save(ponto);
    }

    public Ponto editarNomePonto(Long pontoId, String novoNome) {
        Ponto ponto = pontoRepository.findById(pontoId)
                .orElseThrow(() -> new PontoNotFoundException(pontoId));

        ponto.setNome(novoNome);
        return pontoRepository.save(ponto);
    }

    public void excluirPonto(Long pontoId) {
        if (!pontoRepository.existsById(pontoId)) {
            throw new PontoNotFoundException(pontoId);
        }
        pontoRepository.deleteById(pontoId);
    }

    public void excluirPontosDoMapa(Long mapaId) {
        if (!mapaRepository.existsById(mapaId)) {
            throw new MapNotFoundException(mapaId);
        }
        pontoRepository.deleteByMapaId(mapaId);
    }
}
