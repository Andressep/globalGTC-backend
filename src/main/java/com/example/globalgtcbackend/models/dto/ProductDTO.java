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
    private String name;
    private int price;
    private double weight;
}
