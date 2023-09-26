package com.example.globalgtcbackend.repository;

import com.example.globalgtcbackend.models.entity.Salesperson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISalespersonDao extends JpaRepository<Salesperson, Integer> {
}
