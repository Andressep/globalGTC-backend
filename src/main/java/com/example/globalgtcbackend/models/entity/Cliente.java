package com.example.globalgtcbackend.models.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
        cotizaciones =new ArrayList<>();
    }

    public Cliente(Integer id, String name, String rut, String email) {
        this.id = id;
        this.name = name;
        this.rut = rut;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Cotizacion> getCotizaciones() {
        return cotizaciones;
    }

    public void setCotizaciones(List<Cotizacion> cotizaciones) {
        this.cotizaciones = cotizaciones;
    }
    public void agregarCotizaciones(Cotizacion cotizacion) {
        cotizaciones.add(cotizacion);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rut='" + rut + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Serial
    private static final long serialVersionUID = 1L;
}
