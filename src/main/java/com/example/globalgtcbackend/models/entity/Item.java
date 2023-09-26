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
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;

    private int quantity; // Cambiado "cantidad" a "quantity"

    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id") // Cambiado "producto_id" a "product_id"
    private Product product; // Cambiado "producto" a "product"

    public Item() {
    }

    public int calculate() {
        return quantity * product.getPrice();
    }

    private static final long serialVersionUID = 1L;
}
