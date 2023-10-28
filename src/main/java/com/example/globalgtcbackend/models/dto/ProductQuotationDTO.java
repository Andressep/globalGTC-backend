package com.example.globalgtcbackend.models.dto;

import com.example.globalgtcbackend.models.entity.Category;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductQuotationDTO {
    private Integer productId;
    private String productCode;
    private String description;
    private Category category;
    private int quantity;
    private double price;
    private double totalPrice;
    private double weightPerMeter;
    private double length;
}
