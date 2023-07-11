package com.example.globalgtcbackend.models.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "productos_cotizacion")
public class ProductoCotizacion implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer id;
    private int cantidad;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public int calcularPrecio() {
        return cantidad * producto.getPrice();
    }
    @Serial
    private static final long serialVersionUID = 1L;
}
