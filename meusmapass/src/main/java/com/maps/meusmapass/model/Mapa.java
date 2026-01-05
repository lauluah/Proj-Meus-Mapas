package com.maps.meusmapass.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "mapa")
public class Mapa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private LocalDateTime dataCriacao;

    @OneToMany(
            mappedBy = "mapa",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Ponto> pontos = new ArrayList<>();

    public Mapa() {

    }
}
