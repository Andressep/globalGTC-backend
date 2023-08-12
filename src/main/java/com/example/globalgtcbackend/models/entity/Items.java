package com.example.globalgtcbackend.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity
@Table(name = "items")
public class Items implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer item_id;
    private int cantidad;
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    public Items() {
    }
    public int calcular() {
        return cantidad * producto.getPrice();
    }

    private static final long serialVersionUID = 1L;
}
