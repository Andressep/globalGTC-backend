package com.example.globalgtcbackend.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer customerId;
    @Column(name = "name", length = 25, nullable = false)
    private String name;
    @Column(name = "phone", length = 15)
    private String phone;
    @Column(name = "rut", length = 15)
    private String rut;
    @Column(name = "adress", length = 25)
    private String address;
    @Column(name = "email", length = 25, nullable = false)
    private String email;
    @JsonIgnoreProperties(value = {"customer", "hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Quotation> quotations;

    public Customer() {
        quotations = new ArrayList<>();
    }

   public void addQuotation(Quotation quotation) {
        quotations.add(quotation);
    }

    private static final long serialVersionUID = 1L;
}
