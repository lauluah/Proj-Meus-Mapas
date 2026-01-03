package com.maps.meusmapass.dto.mapper;

import com.maps.meusmapass.dto.request.MapaRequestDTO;
import com.maps.meusmapass.dto.response.MapaResponseDTO;
import com.maps.meusmapass.model.Mapa;

import java.time.LocalDateTime;


public class MapaDTOMapper {

    public static Mapa toEntity(MapaRequestDTO dto) {
        Mapa mapa = new Mapa();
        mapa.setNome(dto.getNome());
        mapa.setDataCriacao(LocalDateTime.now());
        return mapa;
    }

    public static MapaResponseDTO toResponse(Mapa mapa) {
        MapaResponseDTO dto = new MapaResponseDTO();
        dto.setId(mapa.getId());
        dto.setNome(mapa.getNome());
        dto.setDataCriacao(mapa.getDataCriacao());
        return dto;
    }
}

