package com.example.globalgtcbackend.repository;

import com.example.globalgtcbackend.models.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductDao extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT * FROM products WHERE description LIKE %?%", nativeQuery = true)
    public List<Product> findByProductName(String term);
}
