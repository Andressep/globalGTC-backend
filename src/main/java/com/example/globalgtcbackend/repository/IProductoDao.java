package com.example.globalgtcbackend.repository;

import com.example.globalgtcbackend.models.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductoDao extends JpaRepository<Producto, Integer> {

    @Query(value = "SELECT * FROM productos WHERE name LIKE %?%", nativeQuery = true)
    public List<Producto> findByNombre(String term);

}
