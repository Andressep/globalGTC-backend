package com.example.globalgtcbackend.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "salesperson")
public class Salesperson implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer SalespersonId;
    @Column(name = "name")
    private String name;

    private static final long serialVersionUID = 1L;

}
