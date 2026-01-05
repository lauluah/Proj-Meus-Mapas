package com.maps.meusmapass.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ponto")
public class Ponto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private Double latitude;
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "mapa_id", nullable = false)
    private Mapa mapa;

    public Ponto() {
    }
}
