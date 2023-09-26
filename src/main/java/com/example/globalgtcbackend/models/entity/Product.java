package com.example.globalgtcbackend.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "products")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "code_id", length = 10)
    private String codeId;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    private String size;

    private int price;

    private double weight;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    private LocalDate createdAt; // Cambiado "createAt" a "createdAt"

    @PrePersist
    public void prePersist() {
        createdAt = LocalDate.now();
    }

    private static final long serialVersionUID = 1L;
}
