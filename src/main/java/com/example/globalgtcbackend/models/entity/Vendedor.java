package com.example.globalgtcbackend.models.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "vendedor")
public class Vendedor implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer vendedor_id;
    @Column(name = "name")
    private String name;

    private static final long serialVersionUID = 1L;

}
