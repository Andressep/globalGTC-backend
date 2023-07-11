package com.example.globalgtcbackend.models.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

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

    public Producto() {
    }

    @PrePersist
    public void prePersist() { createAt = LocalDate.now(); }

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", createAt=" + createAt +
                '}';
    }

    @Serial
    private static final long serialVersionUID = 1L;
}
