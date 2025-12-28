package com.maps.meusmapass.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Mapa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private LocalDateTime dataCriacao;

    @OneToMany(mappedBy = "mapa", cascade = CascadeType.ALL)
    private List<Ponto> pontos;

    public Mapa() {

    }
}
