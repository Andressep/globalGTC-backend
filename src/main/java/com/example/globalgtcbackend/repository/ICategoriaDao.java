package com.example.globalgtcbackend.repository;

import com.example.globalgtcbackend.models.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaDao extends JpaRepository<Categoria, Integer> {
}
