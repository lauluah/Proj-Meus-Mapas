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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public PontoResponseDTO criarPonto(Long mapaId, PontoRequestDTO dto) {
        Mapa mapa = mapaRepository.findById(mapaId)
                .orElseThrow(() -> new MapNotFoundException(mapaId));

        Ponto ponto = PontoDTOMapper.toEntity(dto);
        ponto.setMapa(mapa);

        Ponto salvo = pontoRepository.save(ponto);
        return PontoDTOMapper.toResponse(salvo);
    }

    @Transactional
    public PontoResponseDTO atualizarPonto(
            Long mapaId,
            Long pontoId,
            PontoRequestDTO dto
    ) {
        Ponto ponto = pontoRepository
                .findByIdAndMapaId(pontoId, mapaId)
                .orElseThrow(() -> new PontoNotFoundException(pontoId));

        ponto.setNome(dto.getNome());
        ponto.setDescricao(dto.getDescricao());

        return PontoDTOMapper.toResponse(ponto);
    }

    @Transactional
    public void excluirPonto(Long pontoId) {
        Ponto ponto = pontoRepository.findById(pontoId)
                .orElseThrow(() -> new PontoNotFoundException(pontoId));

        pontoRepository.delete(ponto);
    }
}