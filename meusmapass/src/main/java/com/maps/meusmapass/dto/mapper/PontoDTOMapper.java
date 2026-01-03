package com.maps.meusmapass.dto.mapper;

import com.maps.meusmapass.dto.request.PontoRequestDTO;
import com.maps.meusmapass.dto.response.PontoResponseDTO;
import com.maps.meusmapass.model.Mapa;
import com.maps.meusmapass.model.Ponto;

public class PontoDTOMapper {

    public static Ponto toEntity(PontoRequestDTO dto) {
        Ponto ponto = new Ponto();
        ponto.setNome(dto.getNome());
        ponto.setDescricao(dto.getDescricao());
        ponto.setLatitude(dto.getLatitude());
        ponto.setLongitude(dto.getLongitude());
        return ponto;
    }

    public static PontoResponseDTO toResponse(Ponto ponto) {
        PontoResponseDTO dto = new PontoResponseDTO();
        dto.setId(ponto.getId());
        dto.setNome(ponto.getNome());
        dto.setDescricao(ponto.getDescricao());
        dto.setLatitude(ponto.getLatitude());
        dto.setLongitude(ponto.getLongitude());
        return dto;
    }
}

