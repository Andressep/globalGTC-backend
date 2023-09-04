package com.example.globalgtcbackend.models.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductoDTO implements Serializable {

    private Integer producto_id;
    private String name;
    private int price;
    private double weight;
}
