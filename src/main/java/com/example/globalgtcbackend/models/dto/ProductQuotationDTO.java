package com.example.globalgtcbackend.models.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductQuotationDTO {
    private Integer productQuotationId;
    private String productCode;
    private String description;
    private int quantity;
    private double price;
    private double totalPrice;
    private double weightPerMeter;
    private String length;
}
