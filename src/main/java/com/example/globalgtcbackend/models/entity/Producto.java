package com.example.globalgtcbackend.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "productos")
public class Producto implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer producto_id;
    private String name;
    private String code_id;
    private String size;
    private int price;
    private double weight;
    @Temporal(TemporalType.DATE)
    @Column(name = "create_at")
    private LocalDate createAt;

    @PrePersist
    public void prePersist() { createAt = LocalDate.now(); }

    private static final long serialVersionUID = 1L;
}
