package com.example.globalgtcbackend.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer cliente_id;
    @Column(name = "name", length = 25, nullable = false)
    private String name;
    @Column(name = "rut", length = 15, nullable = false)
    private String rut;
    @Column(name = "email", length = 25, nullable = false)
    private String email;
    @JsonIgnoreProperties(value = {"cliente", "hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Cotizacion> cotizaciones;

    public Cliente() {
        cotizaciones = new ArrayList<>();
    }

   public void agregarCotizaciones(Cotizacion cotizacion) {
        cotizaciones.add(cotizacion);
    }

    private static final long serialVersionUID = 1L;
}
