package com.example.globalgtcbackend.repository;

import com.example.globalgtcbackend.models.entity.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVendedorDao extends JpaRepository<Vendedor, Integer> {
}
