package com.maps.meusmapass.service;

import com.maps.meusmapass.dto.mapper.PontoDTOMapper;
import com.maps.meusmapass.dto.request.PontoRequestDTO;
import com.maps.meusmapass.dto.response.PontoResponseDTO;
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

    public List<PontoResponseDTO> listarPontosPorMapa(Long mapaId) {

        if (!mapaRepository.existsById(mapaId)) {
            throw new MapNotFoundException(mapaId);
        }

        return pontoRepository.findByMapaId(mapaId)
                .stream()
                .map(PontoDTOMapper::toResponse)
                .toList();
    }

    public PontoResponseDTO criarPonto(Long mapaId, PontoRequestDTO dto) {

        Mapa mapa = mapaRepository.findById(mapaId)
                .orElseThrow(() -> new MapNotFoundException(mapaId));

        Ponto pontoEntity = PontoDTOMapper.toEntity(dto);
        pontoEntity.setMapa(mapa);
        Ponto createdPonto = pontoRepository.save(pontoEntity);
        return PontoDTOMapper.toResponse(createdPonto);
    }


    public PontoResponseDTO editarNomePonto(Long pontoId, String novoNome, String novaDescricao) {

        Ponto ponto = pontoRepository.findById(pontoId)
                .orElseThrow(() -> new PontoNotFoundException(pontoId));

        ponto.setNome(novoNome);
        ponto.setDescricao(novaDescricao);
        pontoRepository.save(ponto);

        return PontoDTOMapper.toResponse(ponto);
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