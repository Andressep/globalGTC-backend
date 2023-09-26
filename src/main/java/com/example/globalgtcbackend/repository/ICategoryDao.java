package com.example.globalgtcbackend.repository;

import com.example.globalgtcbackend.models.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryDao extends JpaRepository<Category, Integer> {
}
