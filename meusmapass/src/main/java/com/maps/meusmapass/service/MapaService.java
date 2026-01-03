package com.maps.meusmapass.service;



import com.maps.meusmapass.dto.mapper.MapaDTOMapper;
import com.maps.meusmapass.dto.request.MapaRequestDTO;
import com.maps.meusmapass.dto.response.MapaResponseDTO;
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

    public MapaResponseDTO criarMapa(MapaRequestDTO dto) {

        Mapa mapa = MapaDTOMapper.toEntity(dto);
        Mapa salvo = mapaRepository.save(mapa);

        return MapaDTOMapper.toResponse(salvo);
    }

    public List<MapaResponseDTO> listarMapas() {
        return mapaRepository.findAll()
                .stream()
                .map(MapaDTOMapper::toResponse)
                .toList();
    }

    public void excluirMapa(Long id) {
        if (!mapaRepository.existsById(id)) {
            throw new MapNotFoundException(id);
        }
        mapaRepository.deleteById(id);
    }
}

