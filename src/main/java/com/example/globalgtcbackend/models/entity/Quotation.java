package com.example.globalgtcbackend.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "quotations")
public class Quotation implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer quotationId;
    @Column(name = "quotation_code", length = 5)
    private String quotationCode;
    @DateTimeFormat(pattern = "dd-MM-yyy")
    @Column(name = "created_at")
    private LocalDate createdAt;
    @JsonIgnoreProperties({"quotations", "hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "salesperson_id")
    private Salesperson salesperson;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "quotation_id")
    private List<QuotationDetails> quotationDetails;

    public Quotation() {
        quotationDetails = new ArrayList<>();
    }
    public Quotation(Integer id, Customer customer, Salesperson salesperson) {
        this.quotationId = id;
        this.customer = customer;
        this.salesperson = salesperson;
        quotationDetails = new ArrayList<>();
    }
    public void addProductosCotizacion(QuotationDetails items) {
        this.quotationDetails.add(items);
    }
    public int getTotal() {
        int total = 0;
        int size = quotationDetails.size();

        for (int i = 0; i < size; i++) {
            total += quotationDetails.get(i).calculate();
        }
        return total;
    }
    @PrePersist
    public void prePersist() {
        createdAt = LocalDate.now();
    }
    private static final long serialVersionUID = 1L;
}
