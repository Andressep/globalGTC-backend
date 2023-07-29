package com.example.globalgtcbackend.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", length = 25, nullable = false)
    private String name;
    @Column(name = "rut", length = 15, nullable = false)
    private String rut;
    @Column(name = "email", length = 25, nullable = false)
    private String email;
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Cotizacion> cotizaciones;

    public Cliente() {
        cotizaciones = new ArrayList<>();
    }

   public void agregarCotizaciones(Cotizacion cotizacion) {
        cotizaciones.add(cotizacion);
    }
    @Serial
    private static final long serialVersionUID = 1L;
}
