package com.example.globalgtcbackend.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "cotizaciones")
public class Cotizacion implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer id;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Column(name = "create_at")
    private LocalDate createAt;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Cliente cliente;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Vendedor vendedor;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cotizacion_id") // Genera su llave foranea en la tabla ProductoCotizacion
    private List<ProductoCotizacion> productos;

    public Cotizacion() {
        productos = new ArrayList<>();
    }
    @PrePersist
    public void prePersist() { createAt = LocalDate.now(); }
    public Cotizacion(Integer id, Cliente cliente, Vendedor vendedor) {
        this.id = id;
        this.cliente = cliente;
        this.vendedor = vendedor;
        productos = new ArrayList<>();
    }
    public void agregarProductos(ProductoCotizacion producto) {
        this.productos.add(producto);
    }
    public int calcularTotal() {
        int total = 0;
        int size = productos.size();

        for (int i = 0; i < size; i++) {
            total += productos.get(i).calcularPrecio();
        }
        return total;
    }
    @Serial
    private static final long serialVersionUID = 1L;
}
