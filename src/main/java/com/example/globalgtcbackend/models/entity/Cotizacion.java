package com.example.globalgtcbackend.models.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public List<ProductoCotizacion> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoCotizacion> productos) {
        this.productos = productos;
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

    @Override
    public String toString() {
        return "Cotizacion{" +
                "id=" + id +
                ", createAt=" + createAt +
                ", cliente=" + cliente +
                ", vendedor=" + vendedor +
                '}';
    }

    @Serial
    private static final long serialVersionUID = 1L;
}
