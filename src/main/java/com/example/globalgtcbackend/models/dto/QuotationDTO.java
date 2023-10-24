package com.example.globalgtcbackend.models.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuotationDTO {
    private Integer quotationId;
    private String createdAt;
    private String customerName;
    private String customerPhoneNumber;
    private String customerAddress;
    private String customerRut;
    private String salespersonName;
    private List<ProductQuotationDTO> quotationDetailsList;
    private double subTotal;
    private double tax;
    private double totalWeight;
    private double totalPayment;
}
