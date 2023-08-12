package com.example.globalgtcbackend.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

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
    private Integer cotizacion_id;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Column(name = "create_at")
    private LocalDate createAt;
    @JsonIgnoreProperties({"cotizaciones", "hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "vendedor_id")
    private Vendedor vendedor;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cotizacion_id")
    private List<Items> productos;

    public Cotizacion() {
        productos = new ArrayList<>();
    }
    @PrePersist
    public void prePersist() { createAt = LocalDate.now(); }
    public Cotizacion(Integer id, Cliente cliente, Vendedor vendedor) {
        this.cotizacion_id = id;
        this.cliente = cliente;
        this.vendedor = vendedor;
        productos = new ArrayList<>();
    }
    public void addProductosCotizacion(Items items) {
        this.productos.add(items);
    }
    public int getTotal() {
        int total = 0;
        int size = productos.size();

        for (int i = 0; i < size; i++) {
            total += productos.get(i).calcular();
        }
        return total;
    }

    private static final long serialVersionUID = 1L;
}
