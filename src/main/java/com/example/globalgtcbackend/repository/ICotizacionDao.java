package com.example.globalgtcbackend.repository;

import com.example.globalgtcbackend.models.entity.Cotizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICotizacionDao extends JpaRepository<Cotizacion, Integer> {
}
