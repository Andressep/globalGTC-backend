package com.example.globalgtcbackend.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoria_id;
    @Column(name = "name")
    private String name;

    private static final long serialVersionUID = 1L;
}
