package com.maps.meusmapass.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Ponto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Double latitude;
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "mapa_id")
    private Mapa mapa;

    public Ponto() {
    }
}
