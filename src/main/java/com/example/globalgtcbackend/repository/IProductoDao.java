package com.example.globalgtcbackend.repository;

import com.example.globalgtcbackend.models.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoDao extends JpaRepository<Producto, Integer> {
}
