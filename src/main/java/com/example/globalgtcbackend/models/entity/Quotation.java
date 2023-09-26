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
    private Integer QuotationId;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyy")
    @Column(name = "create_at")
    private LocalDate createAt;
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
    private List<Item> items;

    public Quotation() {
        items = new ArrayList<>();
    }
    @PrePersist
    public void prePersist() { createAt = LocalDate.now(); }
    public Quotation(Integer id, Customer customer, Salesperson salesperson) {
        this.QuotationId = id;
        this.customer = customer;
        this.salesperson = salesperson;
        items = new ArrayList<>();
    }
    public void addProductosCotizacion(Item items) {
        this.items.add(items);
    }
    public int getTotal() {
        int total = 0;
        int size = items.size();

        for (int i = 0; i < size; i++) {
            total += items.get(i).calculate();
        }
        return total;
    }

    private static final long serialVersionUID = 1L;
}
