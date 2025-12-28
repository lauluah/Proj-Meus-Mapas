package com.maps.meusmapass.repository;

import com.maps.meusmapass.model.Ponto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PontoRepository extends JpaRepository<Ponto, Long> {

    List<Ponto> findByMapaId(Long mapaId);
    void deleteByMapaId(Long mapaId);
}
