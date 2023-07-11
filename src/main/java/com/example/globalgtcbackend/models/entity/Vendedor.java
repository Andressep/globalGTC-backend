package com.example.globalgtcbackend.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "vendedor")
public class Vendedor {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;

    public Vendedor() {
    }

    public Vendedor(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Vendedor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
