package com.example.globalgtcbackend.models.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDTO implements Serializable {

    private Integer productId;
    private String description;
    private double price;
    private double weight;
}
