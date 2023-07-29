package com.example.globalgtcbackend.models.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "productos")
public class Producto implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String size;
    private int price;
    private int weight;
    @Temporal(TemporalType.DATE)
    @Column(name = "create_at")
    private LocalDate createAt;
    @PrePersist
    public void prePersist() { createAt = LocalDate.now(); }
    @Serial
    private static final long serialVersionUID = 1L;
}
